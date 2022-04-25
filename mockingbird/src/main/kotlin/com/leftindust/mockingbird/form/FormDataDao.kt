package com.leftindust.mockingbird.form

import com.google.gson.JsonElement
import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.patient.GraphQLPatient

interface FormDataDao {
    @Blocking
    fun attachForm(patient: GraphQLPatient.ID, form: JsonElement, requester: MediqToken): FormData

    @Blocking
    fun getForms(patient: GraphQLPatient.ID, requester: MediqToken): List<FormData>
}