package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.AssignedForm
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.impl.repository.HibernateAssignedFormRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorPatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateEventRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateFormRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernatePatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateVisitRepository
import com.leftindust.mockingbird.dao.patient.CreatePatientDao
import com.leftindust.mockingbird.dao.patient.DeletePatientDao
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import com.leftindust.mockingbird.dao.patient.UpdatePatientDao
import com.leftindust.mockingbird.extensions.getByIds
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLPatientInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.search.example.GraphQLPatientExample
import javax.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Transactional
@Repository
class PatientDaoImpl(
    @Autowired authorizer: Authorizer,
    @Autowired private val patientRepository: HibernatePatientRepository,
    @Autowired private val doctorRepository: HibernateDoctorRepository,
    @Autowired private val doctorPatientRepository: HibernateDoctorPatientRepository,
    @Autowired private val eventRepository: HibernateEventRepository,
    @Autowired private val visitRepository: HibernateVisitRepository,
    @Autowired private val entityManager: EntityManager,
    @Autowired private val formRepository: HibernateFormRepository,
    @Autowired private val assignedFormRepository: HibernateAssignedFormRepository,
) : ReadPatientDao, DeletePatientDao, UpdatePatientDao, CreatePatientDao, AbstractHibernateDao(authorizer) {
    companion object {
        private val updatePatients = Crud.UPDATE to Tables.Patient
        private val readPatients = Crud.READ to Tables.Patient
        private val createPatients = Crud.CREATE to Tables.Patient
        private val readEvents = Crud.READ to Tables.Event
        private val updateDoctor = Crud.UPDATE to Tables.Doctor
        private val deletePatient = Crud.DELETE to Tables.Patient
        private val updatePatientsAndDoctors = updatePatients + updateDoctor
        private val readPatientsAndEvents = readPatients + readEvents
    }

    override fun getByPID(
        pid: GraphQLPatient.ID,
        requester: MediqToken
    ): Patient = if (requester can readPatients) {
        patientRepository.getById(pid.id)
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }

    override fun addNewPatient(
        patient: GraphQLPatientInput,
        requester: MediqToken
    ): Patient = if (requester can listOf(createPatients)) {
        val newPatient = Patient(patient, entityManager)
        patientRepository.save(newPatient)
    } else {
        throw NotAuthorizedException(requester, createPatients, updatePatients)
    }


    override fun removeByPID(
        pid: GraphQLPatient.ID,
        requester: MediqToken
    ): Patient = if (requester can deletePatient) {
        val toBeDeleted = patientRepository.getById(pid.id)
        patientRepository.delete(toBeDeleted)
        toBeDeleted
    } else {
        throw NotAuthorizedException(requester, Crud.DELETE to Tables.Patient)
    }

    override fun getByDoctor(
        did: GraphQLDoctor.ID,
        requester: MediqToken
    ): Collection<Patient> = if (requester can readPatients) {
        val doctor = doctorRepository.getById(did.id)
        doctorPatientRepository.getAllByDoctorId(doctor.id!!).map { it.patient }
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }

    override fun getVisitPatients(
        vid: GraphQLVisit.ID,
        requester: MediqToken
    ): Collection<Patient> = if (requester can readPatients) {
        visitRepository.getByIdWithEventPatients(vid.id).event.patients
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }


    override fun addDoctorToPatient(
        pid: GraphQLPatient.ID,
        did: GraphQLDoctor.ID,
        requester: MediqToken
    ): Patient = if (requester can updatePatientsAndDoctors) {
        val patient = patientRepository.getById(pid.id)
        val doctor = doctorRepository.getById(did.id)
        patient.addDoctor(doctor)
    } else {
        throw NotAuthorizedException(requester, updatePatientsAndDoctors)
    }

    override fun getMany(
        range: GraphQLRangeInput,
        sortedBy: Patient.SortableField,
        requester: MediqToken
    ): Collection<Patient> = if (requester can readPatients) {
        patientRepository.findAll(range.toPageable(Sort.by(sortedBy.fieldName))).content
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }

    override fun update(
        patientInput: GraphQLPatientEditInput,
        requester: MediqToken
    ): Patient = if (requester can updatePatients) {
        val patient = patientRepository.getById(patientInput.pid.id)
        patient.apply {
            setByGqlInput(patientInput, entityManager)
        }
    } else {
        throw NotAuthorizedException(requester, updatePatients)
    }


    override fun getByEvent(
        eid: GraphQLEvent.ID,
        requester: MediqToken
    ): Collection<Patient> = if (requester can readPatientsAndEvents) {
        eventRepository.getByIdWithPatients(eid.id).patients
    } else {
        throw NotAuthorizedException(requester, readPatientsAndEvents)
    }

    override fun getPatientsByPids(
        pids: List<GraphQLPatient.ID>,
        requester: MediqToken
    ): Collection<Patient> = if (requester can readPatients) {
        patientRepository.findAllById(pids.map { it.id })
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }

    override fun searchByExample(
        example: GraphQLPatientExample,
        requester: MediqToken
    ): Collection<Patient> = if (requester can readPatients) {

        val criteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery = criteriaBuilder.createQuery(Patient::class.java)
        val root = criteriaQuery.from(Patient::class.java)

        val predicate = example.toPredicate(criteriaBuilder, root)

        criteriaQuery.where(predicate)

        val patientQuery = entityManager.createQuery(criteriaQuery.where(predicate))

        patientQuery.resultList
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }


    override fun getByUser(
        uid: String,
        requester: MediqToken
    ): Patient? = if (requester can readPatients) {
        patientRepository.findByUser_UniqueId(uid)
    } else {
        throw NotAuthorizedException(requester, readPatients)
    }

    override fun assignForms(
        patients: List<GraphQLPatient.ID>,
        survey: GraphQLFormTemplate.ID,
        requester: MediqToken
    ): Collection<Patient> = if (requester can updatePatients) {
        val form = formRepository.getById(survey.id)
        val patientEntities = patientRepository.getByIds(patients.map { it.id })
        for (patient in patientEntities) {
            val assignedForm = assignedFormRepository.save(AssignedForm(form, patient))
            patient.assignedForms.add(assignedForm)
        }
        patientEntities
    } else {
        throw NotAuthorizedException(requester, updatePatients)
    }

    override fun necessaryPermissions() = ReadPatientDao.necessaryPermissions +
            CreatePatientDao.necessaryPermissions +
            DeletePatientDao.necessaryPermissions +
            UpdatePatientDao.necessaryPermissions
}

private operator fun Pair<Crud, Tables>.plus(other: Pair<Crud, Tables>): List<Pair<Crud, Tables>> = listOf(this, other)
