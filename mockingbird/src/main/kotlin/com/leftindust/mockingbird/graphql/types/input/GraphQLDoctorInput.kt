package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient

@GraphQLName("DoctorInput")
data class GraphQLDoctorInput(
    val user: GraphQLUserInput? = null,
    val nameInfo: GraphQLNameInfoInput,
    val phones: List<GraphQLPhoneInput>? = emptyList(),
    val title: String? = null,
    val clinic: GraphQLClinic.ID? = null,
    val dateOfBirth: GraphQLDateInput? = null,
    val addresses: List<GraphQLAddressInput>? = emptyList(),
    val emails: List<GraphQLEmailInput>? = emptyList(),
    val patients: List<GraphQLPatient.ID>? = emptyList(),
) {
    init {
        if (user?.nameInfo == nameInfo || user == null) {
            // ok
        } else {
            throw IllegalArgumentException("nameInfo for the user and the doctor must be the same")
        }
    }
}

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