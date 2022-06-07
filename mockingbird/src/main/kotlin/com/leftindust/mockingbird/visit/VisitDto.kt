package com.leftindust.mockingbird.visit

import com.leftindust.mockingbird.event.EventDto
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.patient.PatientDto
import java.util.UUID

data class VisitDto(
    override val id: VisitDtoId,
    val title: String?,
    val description: String?,
) : AbstractGraphQLDto<VisitDto.VisitDtoId>() {
    data class VisitDtoId(override val value: UUID) : GraphQLID<UUID>

    suspend fun event(): EventDto = TODO()

    suspend fun patients(): List<PatientDto> = TODO()

    // todo suspend fun icds(): List<Icd>
}