package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
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