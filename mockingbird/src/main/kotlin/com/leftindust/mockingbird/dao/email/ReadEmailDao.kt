package com.leftindust.mockingbird.dao.email

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Email
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEmergencyContact
import com.leftindust.mockingbird.patient.GraphQLPatient

interface ReadEmailDao {
    @Blocking
    fun getDoctorEmails(did: GraphQLDoctor.ID, mediqAuthToken: MediqToken): List<Email>
    @Blocking
    fun getEmergencyContactEmails(ecid: GraphQLEmergencyContact.ID, mediqAuthToken: MediqToken): List<Email>
    @Blocking
    fun getPatientEmails(pid: GraphQLPatient.ID, authContext: MediqToken): List<Email>
}