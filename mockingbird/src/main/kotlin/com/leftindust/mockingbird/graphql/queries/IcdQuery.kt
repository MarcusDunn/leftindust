package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.extensions.parallelMap
import com.leftindust.mockingbird.external.icd.IcdFetcher
import com.leftindust.mockingbird.graphql.types.icd.GraphQLFoundationIcdCode
import com.leftindust.mockingbird.graphql.types.icd.GraphQLIcdLinearizationEntity
import com.leftindust.mockingbird.graphql.types.icd.GraphQLIcdSearchResult
import org.springframework.stereotype.Component

@Component
class IcdQuery(
    private val client: IcdFetcher,
) : Query {
    private companion object {
        const val flexiSearchDefaultValue = true
        const val flatResultsDefaultValue = true
        val linearizationDefaultValue = IcdFetcher.Linearization.DEFAULT
    }


    suspend fun searchIcd(
        @GraphQLDescription("Cannot be empty string")
        query: String,
        linearization: IcdFetcher.Linearization? = linearizationDefaultValue,
        flexiSearch: Boolean? = flexiSearchDefaultValue,
        flatResults: Boolean? = flatResultsDefaultValue,
        authContext: GraphQLAuthContext
    ): GraphQLIcdSearchResult {
        if (authContext.mediqAuthToken.isVerified()) {
            val nnLinearization = linearization ?: linearizationDefaultValue
            val nnFlatResults = flatResults ?: flatResultsDefaultValue
            val nnFlexiSearch = flexiSearch ?: flexiSearchDefaultValue

            return if (query.isNotEmpty()) {
                client.linearizationSearch(
                    query,
                    linearization = nnLinearization,
                    flatResults = nnFlatResults,
                    flexiSearch = nnFlexiSearch
                )
                    .let { searchResult ->
                        searchResult.copy(destinationEntities = searchResult.destinationEntities?.distinctBy { it.id })
                    }
            } else {
                throw GraphQLKotlinException("cannot query with empty string")
            }
        } else throw GraphQLKotlinException("not authorized")
    }

    suspend fun icd(
        icdCode: String,
        authContext: GraphQLAuthContext
    ): GraphQLIcdLinearizationEntity {
        return if (authContext.mediqAuthToken.isVerified()) {
            client.linearizationEntity(GraphQLFoundationIcdCode(icdCode))
        } else {
            throw NotAuthorizedException(authContext.mediqAuthToken, Crud.READ to Tables.IcdCode)
        }
    }

    suspend fun icds(icdCodes: List<String>, authContext: GraphQLAuthContext) =
        icdCodes.parallelMap { icd(it, authContext) }
}