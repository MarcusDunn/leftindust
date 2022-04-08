package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.server.operations.Query
import com.github.wnameless.json.flattener.JsonFlattener
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import org.springframework.stereotype.Component

@Component
class ConverterQuery : Query {
    data class Csv(val inner: List<Map<String, JsonElement>>) {
        override fun toString(): String {
            val headers = inner.flatMap { it.keys }.distinct()
            return StringBuilder().apply {
                for ((i, inner) in headers.withIndex()) {
                    append(inner)
                    append(if (i == headers.size - 1) "\n" else ",")
                }
                for (map in inner) {
                    for ((i, header) in headers.withIndex()) {
                        append(map[header])
                        append(if (i == headers.size - 1) "\n" else ",")
                    }
                }
            }.toString()
        }
    }

    enum class ConvertTarget {
        Json,
        Csv,
    }

    fun convert(json: String, target: ConvertTarget, authContext: GraphQLAuthContext): String {
        val asJsonArray = JsonParser.parseString(json).asJsonArray
        return when (target) {
            ConvertTarget.Json -> asJsonArray.toString()
            ConvertTarget.Csv -> Csv(asJsonArray
                .map { jsonElement -> JsonFlattener.flatten(jsonElement.toString()) }
                .map { flattened -> JsonParser.parseString(flattened).asJsonObject }
                .map { jsonObject -> jsonObject.keySet().map { key -> key to jsonObject[key] } }
                .map { it.toMap() }).toString()
        }
    }
}