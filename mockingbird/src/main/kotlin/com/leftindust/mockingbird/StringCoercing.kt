package com.leftindust.mockingbird

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingSerializeException

inline fun <reified T : Any> StringCoercing(crossinline parse: (String) -> T, crossinline unparse: (T) -> String) = object : Coercing<T, String> {
    override fun serialize(dataFetcherResult: Any): String {
        return try {
            if (dataFetcherResult is T) {
                unparse(dataFetcherResult)
            } else {
                throw CoercingSerializeException("${T::class.simpleName} must be a ${T::class.simpleName}, instead was $dataFetcherResult")
            }
        } catch (e: Exception) {
            throw CoercingSerializeException("failed to coerce $dataFetcherResult to String", e)
        }
    }

    override fun parseValue(input: Any): T {
        if (input is String) {
            try {
                return parse(input)
            } catch (e: Exception) {
                throw CoercingSerializeException("failed to coerce $input to ${T::class.simpleName}", e)
            }
        } else {
            throw CoercingSerializeException("${T::class.simpleName} must be a string, instead was $input")
        }
    }

    override fun parseLiteral(input: Any): T {
        if (input is StringValue) {
            try {
                return parse(input.value)
            } catch (e: Exception) {
                throw CoercingSerializeException("failed to coerce $input", e)
            }
        } else {
            throw CoercingSerializeException("${T::class.simpleName} must be a StringValue, instead was $input")
        }
    }
}