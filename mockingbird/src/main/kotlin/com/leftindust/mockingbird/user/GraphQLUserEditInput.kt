package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Deletable

data class GraphQLUserEditInput(
    val uid: String,
    val group: Deletable<MediqGroupDto.MediqGroupId>,
    val doctor: Deletable<DoctorDto.DoctorDtoId>,
)