package com.leftindust.mockingbird.record

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import java.time.ZonedDateTime
import java.util.*

data class RecordDto(
    override val id: RecordDtoId,
    val creationDate: ZonedDateTime,
    val type: RecordType,
    val jsonBlob: String,
) : AbstractGraphQLDto<RecordDto.RecordDtoId>() {
    data class RecordDtoId(override val value: UUID) : GraphQLID<UUID>
}
