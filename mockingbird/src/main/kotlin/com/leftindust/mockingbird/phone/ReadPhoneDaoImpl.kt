package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.contact.GraphQLEmergencyContact
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadPhoneDaoImpl : ReadPhoneDao {
    override fun getDoctorPhones(did: GraphQLDoctor.ID, authToken: MediqToken): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun getEmergencyContactPhones(ecid: GraphQLEmergencyContact.ID, authToken: MediqToken): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun getPatientPhones(pid: GraphQLPatient.ID, authToken: MediqToken): List<Phone> {
        TODO("Not yet implemented")
    }
}