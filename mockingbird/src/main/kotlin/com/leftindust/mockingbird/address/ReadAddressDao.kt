package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient

interface ReadAddressDao {
    @Blocking
    fun getDoctorAddresses(did: GraphQLDoctor.ID, authToken: MediqToken): List<Address>

    @Blocking
    fun getPatientAddresses(pid: GraphQLPatient.ID, authToken: MediqToken): List<Address>
}
