package com.leftindust.mockingbird.patient

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.generator.scalars.ID
import com.leftindust.mockingbird.address.GraphQLAddressInput
import com.leftindust.mockingbird.contact.GraphQLEmergencyContactInput
import com.leftindust.mockingbird.email.GraphQLEmailInput
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.*
import com.leftindust.mockingbird.person.GraphQLNameInfoInput
import com.leftindust.mockingbird.phone.GraphQLPhoneInput

@GraphQLName("PatientInput")
@GraphQLDescription(
    """
   The input side of Patient. Note that relations to other types are passed as ID's
   if you want to clear a list, pass an empty list, explicitly setting a list to null or leaving blank will have no effect on
   update operations and will result in empty list for create operations.
   """
)
data class GraphQLPatientInput(
    @GraphQLDescription("required")
    val nameInfo: GraphQLNameInfoInput,
    @GraphQLDescription("defaults to emptyList")
    val phones: List<GraphQLPhoneInput>? = null,
    @GraphQLDescription("required")
    val dateOfBirth: GraphQLDateInput,
    @GraphQLDescription("defaults to emptyList")
    val addresses: List<GraphQLAddressInput>? = null,
    @GraphQLDescription("defaults to emptyList")
    val emails: List<GraphQLEmailInput>? = null,
    @GraphQLDescription("defaults to null")
    val insuranceNumber: ID? = null,
    @GraphQLDescription("required")
    val sex: Sex,
    @GraphQLDescription("defaults to sex")
    val gender: String? = null,
    @GraphQLDescription("defaults to null")
    val ethnicity: Ethnicity? = null,
    @GraphQLDescription("defaults to emptyList")
    val emergencyContacts: List<GraphQLEmergencyContactInput>? = null,
    @GraphQLDescription("defaults to emptyList")
    val doctors: List<GraphQLDoctor.ID>? = null,
    @GraphQLDescription("base64 representation of an image. cannot be over 10 000 characters")
    val thumbnail: String? = null
)

