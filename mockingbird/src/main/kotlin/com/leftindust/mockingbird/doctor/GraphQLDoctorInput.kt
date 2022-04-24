package com.leftindust.mockingbird.doctor

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.address.GraphQLAddressInput
import com.leftindust.mockingbird.clinic.GraphQLClinic
import com.leftindust.mockingbird.email.GraphQLEmailInput
import com.leftindust.mockingbird.graphql.types.input.*
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.person.GraphQLNameInfoInput
import com.leftindust.mockingbird.phone.GraphQLPhoneInput
import com.leftindust.mockingbird.user.GraphQLUserInput

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

