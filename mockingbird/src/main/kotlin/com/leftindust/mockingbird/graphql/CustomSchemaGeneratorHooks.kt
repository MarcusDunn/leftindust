package com.leftindust.mockingbird.graphql

import com.expediagroup.graphql.generator.federation.FederatedSchemaGeneratorHooks
import com.expediagroup.graphql.generator.federation.execution.FederatedTypeResolver
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KType

class CustomSchemaGeneratorHooks(resolvers: List<FederatedTypeResolver<*>>) : FederatedSchemaGeneratorHooks(resolvers) {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        UUID::class -> graphqlUUIDType
        Long::class -> graphqlLongType
        else -> null
    }

    private val graphqlLongType: GraphQLScalarType = GraphQLScalarType.newScalar()
        .name("Long")
        .description("A 64 bit integer serialized as a string")
        .coercing(object : Coercing<Long, String> {
            override fun serialize(dataFetcherResult: Any): String = dataFetcherResult.toString()
            override fun parseValue(input: Any): Long = serialize(input).toLong()
            override fun parseLiteral(input: Any): Long = (input as StringValue).value.toLong()
        })
        .build()

    private val graphqlUUIDType: GraphQLScalarType = GraphQLScalarType.newScalar()
        .name("UUID")
        .description("A type representing a formatted java.util.UUID")
        .coercing(object : Coercing<UUID, String> {
            override fun parseValue(input: Any): UUID = UUID.fromString(serialize(input))
            override fun parseLiteral(input: Any): UUID = UUID.fromString((input as StringValue).value)
            override fun serialize(dataFetcherResult: Any): String = dataFetcherResult.toString()
        })
        .build()
}