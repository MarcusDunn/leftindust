package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@GraphQLName("RangeInput")
data class GraphQLRangeInput(
    val from: Int,
    val to: Int,
) {
    @GraphQLIgnore
    fun toIntRange(): IntRange {
        return from..to
    }

    @GraphQLIgnore
    fun toPageable(sort: Sort = Sort.unsorted()): Pageable {
        val size = to - from
        val page = to / size - 1
        return PageRequest.of(page, size, sort)
    }
}