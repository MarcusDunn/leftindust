package com.leftindust.mockingbird.dao.address.impl

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.address.ReadAddressDao
import com.leftindust.mockingbird.dao.entity.Address
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class ReadAddressDaoImpl : ReadAddressDao {
    override fun getDoctorAddresses(did: GraphQLDoctor.ID, authToken: MediqToken): List<Address> {
        TODO("Not yet implemented")
    }

    override fun getPatientAddresses(pid: GraphQLPatient.ID, authToken: MediqToken): List<Address> {
        TODO("Not yet implemented")
    }
}