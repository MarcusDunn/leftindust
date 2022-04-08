package com.leftindust.mockingbird.dao.email.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud.READ
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.Tables.Doctor
import com.leftindust.mockingbird.dao.Tables.EmergencyContact
import com.leftindust.mockingbird.dao.Tables.Patient
import com.leftindust.mockingbird.dao.email.ReadEmailDao
import com.leftindust.mockingbird.dao.entity.Email
import com.leftindust.mockingbird.dao.impl.AbstractHibernateDao
import com.leftindust.mockingbird.dao.impl.repository.HibernateContactRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernatePatientRepository
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import javax.transaction.Transactional
import org.hibernate.Hibernate
import org.springframework.stereotype.Repository

@Transactional
@Repository
class ReadEmailDaoImpl(
    val doctorRepository: HibernateDoctorRepository,
    val emergencyContactRepository: HibernateContactRepository,
    val patientRepository: HibernatePatientRepository,
    authorizer: Authorizer,
) : ReadEmailDao, AbstractHibernateDao(authorizer) {
    companion object {
        private val readPatient = READ to Patient
        private val readEmergencyContact = READ to EmergencyContact
        private val readDoctor = READ to Doctor
    }

    override fun getDoctorEmails(
        did: GraphQLDoctor.ID,
        mediqAuthToken: MediqToken
    ): List<Email> = if (mediqAuthToken can readDoctor) {
        doctorRepository.getById(did.id).email
            .apply(Hibernate::initialize)
            .toList()
    } else {
        throw NotAuthorizedException(mediqAuthToken, readDoctor)
    }

    override fun getEmergencyContactEmails(
        ecid: GraphQLEmergencyContact.ID,
        mediqAuthToken: MediqToken
    ): List<Email> = if (mediqAuthToken can readEmergencyContact) {
        emergencyContactRepository.getById(ecid.id).email
            .apply(Hibernate::initialize)
            .toList()
    } else {
        throw NotAuthorizedException(mediqAuthToken, readEmergencyContact)
    }

    override fun getPatientEmails(
        pid: GraphQLPatient.ID,
        authContext: MediqToken
    ): List<Email> = if (authContext can readPatient) {
        patientRepository.getById(pid.id).email
            .apply(Hibernate::initialize)
            .toList()
    } else {
        throw NotAuthorizedException(authContext, readPatient)
    }
}