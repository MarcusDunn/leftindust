package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.VisitDao
import com.leftindust.mockingbird.dao.entity.Visit
import com.leftindust.mockingbird.dao.impl.repository.HibernateEventRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernatePatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateVisitRepository
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLVisitEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLVisitInput
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class VisitDaoImpl(
    authorizer: Authorizer,
    private val eventRepository: HibernateEventRepository,
    private val visitRepository: HibernateVisitRepository,
    private val sessionFactory: SessionFactory,
    private val patientRepository: HibernatePatientRepository,
) : VisitDao, AbstractHibernateDao(authorizer) {
    companion object {
        private val readVisits = Crud.READ to Tables.Visit
        private val readEvents = Crud.READ to Tables.Event
        private val editVisits = Crud.UPDATE to Tables.Visit
        private val readEventsAndVisits = listOf(readEvents, readVisits)
        private val requiredPermissions = listOf(
            Crud.READ to Tables.Doctor,
            Crud.READ to Tables.Patient,
            Crud.CREATE to Tables.Visit,
        )
    }

    override fun getVisitByVid(
        vid: GraphQLVisit.ID,
        requester: MediqToken
    ): Visit = if (requester can readVisits) {
        visitRepository.getById(vid.id)
    } else {
        throw NotAuthorizedException(requester, readVisits)
    }


    override fun addVisit(
        visitInput: GraphQLVisitInput,
        requester: MediqToken
    ): Visit = if (requester can requiredPermissions) {
        val event = eventRepository.getById(visitInput.eid.id)
        visitRepository.save(Visit(visitInput, event))
    } else {
        throw NotAuthorizedException(requester, *requiredPermissions.toTypedArray())
    }


    override fun findByEvent(
        eid: GraphQLEvent.ID,
        requester: MediqToken
    ): Visit? = if (requester can readVisits) {
        visitRepository.findByEvent_Id(eid.id)
    } else {
        throw NotAuthorizedException(requester, readVisits)
    }

    override fun getPatientVisits(
        pid: GraphQLPatient.ID,
        requester: MediqToken
    ): List<Visit> = if (requester can readEventsAndVisits) {
        patientRepository.getById(pid.id).events
            .mapNotNull { visitRepository.findByEvent_Id(it.id!!) }
    } else {
        throw NotAuthorizedException(requester, readEventsAndVisits)
    }


    override fun editVisit(
        visit: GraphQLVisitEditInput,
        requester: MediqToken
    ): Visit = if (requester can editVisits) {
        val visitEntity =
            visitRepository.getById(visit.vid.id)
        visitEntity.setByGqlInput(visit, sessionFactory.currentSession)
        visitEntity
    } else {
        throw NotAuthorizedException(requester, editVisits)
    }
}