package com.leftindust.mockingbird.doctor

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.address.GraphQLAddressInput
import com.leftindust.mockingbird.clinic.GraphQLClinic
import com.leftindust.mockingbird.email.GraphQLEmailInput
import com.leftindust.mockingbird.graphql.types.input.*
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.person.GraphQLNameInfoEditInput
import com.leftindust.mockingbird.phone.GraphQLPhoneInput

@GraphQLName("DoctorEditInput")
data class GraphQLDoctorEditInput(
    val did: GraphQLDoctor.ID,
    val userUid: String? = null,
    val nameInfo: GraphQLNameInfoEditInput? = null,
    val phones: List<GraphQLPhoneInput>? = emptyList(),
    val title: String? = null,
    @GraphQLDescription("setting to null will remove the doctor from the clinic")
    val clinics: List<GraphQLClinic.ID>? = null,
    val dateOfBirth: GraphQLDateInput? = null,
    val addresses: List<GraphQLAddressInput>? = null,
    val emails: List<GraphQLEmailInput>? = null,
    val patients: List<GraphQLPatient.ID>? = null,
)