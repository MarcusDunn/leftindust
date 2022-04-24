package com.leftindust.mockingbird.event

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.persistance.AbstractHibernateDao
import com.leftindust.mockingbird.doctor.HibernateDoctorRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.visit.HibernateVisitRepository
import com.leftindust.mockingbird.extensions.getByIds
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.visit.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLTimeRangeInput
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class EventDaoImpl(
    @Autowired private val hibernateEventRepository: HibernateEventRepository,
    @Autowired private val hibernatePatientRepository: HibernatePatientRepository,
    @Autowired private val hibernateDoctorRepository: HibernateDoctorRepository,
    @Autowired private val hibernateVisitRepository: HibernateVisitRepository,
    @Autowired authorizer: Authorizer
) : EventDao, AbstractHibernateDao(authorizer) {
    override fun addEvent(
        event: GraphQLEventInput,
        requester: MediqToken
    ): Event {
        if (requester can (Crud.CREATE to Tables.Event)) {
            val patients =
                event.patients?.let { hibernatePatientRepository.getByIds(it.map { pid -> pid.id }) } ?: emptySet()
            val doctors =
                event.doctors?.let { hibernateDoctorRepository.getByIds(it.map { did -> did.id }) } ?: emptySet()

            val newEvent = Event(event, doctors, patients)
            val eventEntity = hibernateEventRepository.save(newEvent)

            patients.forEach { it.addEvent(eventEntity) }
            doctors.forEach { it.addEvent(eventEntity) }

            return eventEntity
        } else {
            throw NotAuthorizedException(requester, Crud.CREATE to Tables.Event)
        }
    }

    override fun getById(eid: GraphQLEvent.ID, requester: MediqToken): Event =
        if (requester can (Crud.READ to Tables.Event)) {
            hibernateEventRepository.getById(eid.id)
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.Event)
        }

    override fun getPatientEvents(pid: GraphQLPatient.ID, requester: MediqToken): Collection<Event> {
        if (requester can listOf(Crud.READ to Tables.Patient, Crud.READ to Tables.Event)) {
            val byId = hibernatePatientRepository.getById(pid.id)
            return byId.events.onEach { Hibernate.initialize(it) }
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.Patient, Crud.READ to Tables.Event)
        }
    }

    override fun getByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): Collection<Event> =
        if (requester can listOf(Crud.READ to Tables.Doctor, Crud.READ to Tables.Event)) {
            hibernateDoctorRepository.getById(did.id).events.apply(Hibernate::initialize)
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.Doctor, Crud.READ to Tables.Event)
        }

    override fun getEventVisit(vid: GraphQLVisit.ID, requester: MediqToken): Event =
        if (requester can listOf(Crud.READ to Tables.Visit, Crud.READ to Tables.Event)) {
            hibernateVisitRepository.getById(vid.id).event
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.Doctor, Crud.READ to Tables.Event)
        }

    override fun editEvent(
        event: GraphQLEventEditInput,
        requester: MediqToken
    ): Event {
        if (requester can (Crud.UPDATE to Tables.Event)) {
            val entity = hibernateEventRepository.getById(event.eid.id)

            if (entity.reoccurrence?.days != null && entity.reoccurrence?.startDate != null && entity.reoccurrence?.endDate != null) {
                throw IllegalArgumentException("cannot call editEvent on a recurring event")
            }

            val doctors = event.doctors?.let { hibernateDoctorRepository.getByIds(it.map { did -> did.id }) }
            val patients = event.patients?.let { hibernatePatientRepository.getByIds(it.map { pid -> pid.id }) }
            entity.update(event, newDoctors = doctors, newPatients = patients)

            return entity
        } else {
            throw NotAuthorizedException(requester, Crud.UPDATE to Tables.Event)
        }
    }

    // this function has some issues. but basically does as follows
    // in the simplest case, if the recurrence settings cover the entirety of the event recurrence, we simply replace
    // the event with a single edited event
    // if the user only wants to edit part of the recurrence period, we create up to 3 events.
    // one for the edited event within the recurrenceSettings time period
    // one for the time before recurrenceSettings.editStart, that is unmodified except for that the recurrence now ends when the edited event begins
    // and finally one for after the edited period, this is also unchanged.
    // keep in mind that recurrenceSettings can potentially cover only the tail or start of the event, in which case we end up with only 2 events
    override fun editRecurringEvent(
        event: GraphQLEventEditInput,
        requester: MediqToken,
        recurrenceSettings: GraphQLRecurrenceEditSettings
    ): Event {
        if (requester can (Crud.UPDATE to Tables.Event)) {
            val entity = hibernateEventRepository.getById(event.eid.id)

            if (entity.reoccurrence?.days == null && entity.reoccurrence?.startDate == null && entity.reoccurrence?.endDate == null) {
                throw IllegalArgumentException("cannot call editRecurringEvent on a non-recurring event")
            }

            val currentStartDate = entity.reoccurrence!!.startDate

            val currentEndDate = entity.reoccurrence!!.endDate

            hibernateEventRepository.delete(entity)

            if (currentEndDate.isAfter(recurrenceSettings.editEnd.toLocalDate())) { // there will be a trailing unedited event
                val trailingEntity = entity.clone().apply {
                    id = null
                    reoccurrence = Reoccurrence(
                        // toMutableList clones the collection to avoid shared references to a collection
                        days = reoccurrence!!.days.toMutableList(),
                        startDate = reoccurrence!!.startDate,
                        endDate = recurrenceSettings.editStart.toLocalDate()
                    )
                }
                hibernateEventRepository.save(trailingEntity)
            }

            if (currentStartDate.isBefore(recurrenceSettings.editStart.toLocalDate())) { // there will be an unedited event prior to the edited one
                val priorEntity = entity.clone().apply {
                    id = null
                    reoccurrence = Reoccurrence(
                        // toMutableList clones the collection to avoid shared references to a collection
                        days = reoccurrence!!.days.toMutableList(),
                        startDate = recurrenceSettings.editEnd.toLocalDate(),
                        endDate = reoccurrence!!.endDate
                    )
                }
                hibernateEventRepository.save(priorEntity)
            }

            val doctors = event.doctors?.let { hibernateDoctorRepository.getByIds(it.map { did -> did.id }) }
            val patients = event.patients?.let { hibernatePatientRepository.getByIds(it.map { pid -> pid.id }) }
            val updatedEntity = entity.clone().apply { // we keep the same id
                reoccurrence = Reoccurrence(
                    startDate = recurrenceSettings.editStart.toLocalDate(),
                    endDate = recurrenceSettings.editEnd.toLocalDate(),
                    // toMutableList clones the collection to avoid shared references to a collection
                    days = reoccurrence!!.days.toMutableList()
                )
                update(event, newDoctors = doctors, newPatients = patients)
            }
            return hibernateEventRepository.save(updatedEntity)
        } else {
            throw NotAuthorizedException(requester, Crud.UPDATE to Tables.Event)
        }
    }

    override fun getBetween(range: GraphQLTimeRangeInput, requester: MediqToken): List<Event> {
        val readEvents = Crud.READ to Tables.Event
        return if (requester can readEvents) {
            hibernateEventRepository.getAllInRangeOrReoccurrenceIsNotNull(
                range.start.toTimestamp(),
                range.end.toTimestamp()
            )
        } else {
            throw NotAuthorizedException(requester, readEvents)
        }
    }
}