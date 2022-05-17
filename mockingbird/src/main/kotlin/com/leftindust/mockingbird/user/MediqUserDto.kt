package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.NameInfoDto
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.patient.PatientDto

data class MediqUserDto(
    override val id: Uid,
    val group: MediqGroupDto? = null,
) : AbstractGraphQLDto<MediqUserDto.Uid>() {
    data class Uid(override val value: String) : GraphQLID<String>

    suspend fun name(): NameInfoDto = TODO()

    suspend fun isRegistered(): Boolean = TODO()

    suspend fun firebaseUserInfo(): GraphQLFirebaseInfo = TODO()

    suspend fun doctor(): DoctorDto? = TODO()

    suspend fun patient(): PatientDto? = TODO()

    suspend fun hasPermission(): Boolean = TODO()
}


