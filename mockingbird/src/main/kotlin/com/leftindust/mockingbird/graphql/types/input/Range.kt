package com.leftindust.mockingbird.graphql.types.input

interface Range {
    val from: Int
    val to: Int
    val size: Int
        get() = to - from
}