package com.leftindust.mockingbird.icd

interface IcdFetcher {
    enum class Linearization(val value: String) {
        MMS("mms");

        companion object {
            val DEFAULT = MMS
        }
    }
    suspend fun search(
        query: String,
        flexiSearch: Boolean = true,
        flatResults: Boolean = true
    ): GraphQLIcdSearchResult

    suspend fun linearizationEntity(code: GraphQLFoundationIcdCode): GraphQLIcdLinearizationEntity
    suspend fun getDetails(code: GraphQLFoundationIcdCode): GraphQLIcdFoundationEntity
    suspend fun linearization(code: GraphQLFoundationIcdCode, linearization: Linearization = Linearization.DEFAULT): GraphQLIcdMultiVersion
    suspend fun linearizationSearch(
        query: String,
        linearization: Linearization = Linearization.DEFAULT,
        flatResults: Boolean = true,
        flexiSearch: Boolean = true,
    ): GraphQLIcdSearchResult
}