package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.contact.GraphQLEmergencyContact
import com.leftindust.mockingbird.patient.GraphQLPatient

interface ReadEmailDao {
    @Blocking
    fun getDoctorEmails(did: GraphQLDoctor.ID, mediqAuthToken: MediqToken): List<Email>
    @Blocking
    fun getEmergencyContactEmails(ecid: GraphQLEmergencyContact.ID, mediqAuthToken: MediqToken): List<Email>
    @Blocking
    fun getPatientEmails(pid: GraphQLPatient.ID, authContext: MediqToken): List<Email>
}