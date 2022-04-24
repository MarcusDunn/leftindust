package com.leftindust.mockingbird.phone

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.contact.GraphQLEmergencyContact
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient

interface ReadPhoneDao {
    @Blocking
    fun getDoctorPhones(did: GraphQLDoctor.ID, authToken: MediqToken): List<Phone>

    @Blocking
    fun getEmergencyContactPhones(ecid: GraphQLEmergencyContact.ID, authToken: MediqToken): List<Phone>

    @Blocking
    fun getPatientPhones(pid: GraphQLPatient.ID, authToken: MediqToken): List<Phone>
}
