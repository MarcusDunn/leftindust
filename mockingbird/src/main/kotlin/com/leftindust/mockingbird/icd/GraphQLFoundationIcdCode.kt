package com.leftindust.mockingbird.icd

data class GraphQLFoundationIcdCode(val url: String) {
    val code: String =
        Regex("""\d{5,}""").find(url)?.value ?: throw IllegalArgumentException("the url must contain a code")

    suspend fun linearizationEntity(icdFetcherService: IcdFetcherService): GraphQLIcdLinearizationEntity = icdFetcherService.linearizationEntity(this)
}

