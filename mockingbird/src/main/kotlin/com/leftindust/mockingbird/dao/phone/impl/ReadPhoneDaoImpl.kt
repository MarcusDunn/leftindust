package com.leftindust.mockingbird.dao.phone.impl

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Phone
import com.leftindust.mockingbird.dao.phone.ReadPhoneDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadPhoneDaoImpl : ReadPhoneDao {
    override fun getDoctorPhones(did: GraphQLDoctor.ID, mediqAuthToken: MediqToken): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun getEmergencyContactPhones(
        ecid: GraphQLEmergencyContact.ID,
        mediqAuthToken: MediqToken
    ): List<Phone> {
        TODO("Not yet implemented")
    }

    override fun getPatientPhones(pid: GraphQLPatient.ID, authContext: GraphQLAuthContext): List<Phone> {
        TODO("Not yet implemented")
    }
}