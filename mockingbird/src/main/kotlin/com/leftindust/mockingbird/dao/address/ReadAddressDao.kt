package com.leftindust.mockingbird.dao.address

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Address
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLPatient

interface ReadAddressDao {
    @Blocking
    fun getDoctorAddresses(did: GraphQLDoctor.ID, authToken: MediqToken): List<Address>

    @Blocking
    fun getPatientAddresses(pid: GraphQLPatient.ID, authToken: MediqToken): List<Address>
}
