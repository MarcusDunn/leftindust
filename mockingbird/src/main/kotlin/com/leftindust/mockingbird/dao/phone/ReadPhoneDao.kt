package com.leftindust.mockingbird.dao.phone

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Phone
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.graphql.types.GraphQLPatient

interface ReadPhoneDao {
    @Blocking
    fun getDoctorPhones(did: GraphQLDoctor.ID, authToken: MediqToken): List<Phone>

    @Blocking
    fun getEmergencyContactPhones(ecid: GraphQLEmergencyContact.ID, authToken: MediqToken): List<Phone>

    @Blocking
    fun getPatientPhones(pid: GraphQLPatient.ID, authToken: MediqToken): List<Phone>
}
