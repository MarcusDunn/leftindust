package com.leftindust.mockingbird.icd

import org.springframework.stereotype.Controller

@Controller
class IcdQueryController(
    private val client: IcdFetcherService,
) {


    suspend fun searchIcd(query: String): GraphQLIcdSearchResult {
        TODO()
    }

    suspend fun icd(icdCode: String): GraphQLIcdLinearizationEntity {
        TODO()
    }

    suspend fun icds(icdCodes: List<String>): Nothing {
        TODO()
    }
}