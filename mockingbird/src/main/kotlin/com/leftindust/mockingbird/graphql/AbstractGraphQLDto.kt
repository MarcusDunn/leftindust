package com.leftindust.mockingbird.graphql

abstract class AbstractGraphQLDto<ID : AbstractGraphQLDto.GraphQLID<*>> {
    interface GraphQLID<T : Any> {
        val value: T
    }

    abstract val id: ID
}
