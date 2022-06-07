package com.leftindust.mockingbird.graphql.types.input

data class RangeDto(
    override val from: Int,
    override val to: Int,
) : Range

