package com.leftindust.mockingbird.graphql.hooks

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
        else -> null
    }
}

val graphqlUUIDType: GraphQLScalarType = GraphQLScalarType.newScalar()
    .name("UUID")
    .description("A type representing a formatted java.util.UUID")
    .coercing(UUIDCoercing)
    .build()

object UUIDCoercing : Coercing<UUID, String> {
    override fun parseValue(input: Any?): UUID = UUID.fromString(serialize(input))

    override fun parseLiteral(input: Any?): UUID? {
        val uuidString = (input as? StringValue)?.value
        return UUID.fromString(uuidString)
    }

    override fun serialize(dataFetcherResult: Any?): String = dataFetcherResult.toString()
}