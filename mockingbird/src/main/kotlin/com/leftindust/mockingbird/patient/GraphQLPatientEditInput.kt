package com.leftindust.mockingbird.patient

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.generator.execution.OptionalInput
import com.expediagroup.graphql.generator.scalars.ID
import com.leftindust.mockingbird.dao.entity.enums.Ethnicity
import com.leftindust.mockingbird.dao.entity.enums.Sex
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.*

@GraphQLName("PatientEditInput")
data class GraphQLPatientEditInput(
    @GraphQLDescription("required. Determines what patient is being updated")
    val pid: GraphQLPatient.ID,
    @GraphQLDescription("setting nameInfoEditInput to null will have no effect on update")
    val nameInfo: GraphQLNameInfoEditInput? = null,
    @GraphQLDescription("setting phoneNumbers to null will have no effect on update. to remove, pass an emptyList")
    val phones: List<GraphQLPhoneInput>? = null,
    @GraphQLDescription("setting dateOfBirth to null will have no effect on update")
    val dateOfBirth: GraphQLDateInput? = null,
    @GraphQLDescription("setting addresses to null will have no effect on update. to remove, pass an emptyList")
    val addresses: List<GraphQLAddressInput>? = null,
    @GraphQLDescription("setting emails to null will have no effect on update. to remove, pass an emptyList")
    val emails: List<GraphQLEmailInput>? = null,
    @GraphQLDescription("setting to null will delete prior insuranceNumber, leaving blank will keep old insuranceNumber")
    val insuranceNumber: OptionalInput<ID> = OptionalInput.Undefined,
    @GraphQLDescription("setting sex to null will have no effect on update")
    val sex: Sex? = null,
    @GraphQLDescription("setting gender to null will have no effect on update")
    val gender: String? = null,
    @GraphQLDescription("setting to null will delete prior ethnicity, leaving blank will keep old ethnicity")
    val ethnicity: OptionalInput<Ethnicity> = OptionalInput.Undefined,
    @GraphQLDescription("setting emergencyContact to null will have no effect on update. to remove, pass an emptyList")
    val emergencyContacts: List<GraphQLEmergencyContactInput>? = null,
    @GraphQLDescription("setting doctors to null will have no effect on update. to remove, pass an emptyList")
    val doctors: List<GraphQLDoctor.ID>? = null,
    @GraphQLDescription("setting to null will delete prior thumbnail, leaving blank will keep old thumbail. Cannot be over 10 000 characters")
    val thumbnail: OptionalInput<String> = OptionalInput.Undefined
)