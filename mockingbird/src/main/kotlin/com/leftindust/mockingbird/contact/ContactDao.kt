package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.patient.GraphQLPatient

interface ContactDao {
    @Blocking
    fun getPatientContacts(pid: GraphQLPatient.ID, requester: MediqToken): Collection<EmergencyContact>
}
