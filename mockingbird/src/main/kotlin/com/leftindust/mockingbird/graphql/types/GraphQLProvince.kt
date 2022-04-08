package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore

interface GraphQLProvince {
    fun asStrings(): List<String>
    fun asShortStrings(): List<String>

    @GraphQLIgnore
    fun longToShort(province: String): String

    @GraphQLIgnore
    fun shortToLong(province: String): String
}