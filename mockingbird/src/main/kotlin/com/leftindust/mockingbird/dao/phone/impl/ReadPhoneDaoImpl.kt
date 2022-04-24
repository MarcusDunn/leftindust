package com.leftindust.mockingbird.dao.phone.impl

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Phone
import com.leftindust.mockingbird.dao.phone.ReadPhoneDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
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