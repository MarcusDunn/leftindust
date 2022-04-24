package com.leftindust.mockingbird.icd

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import org.springframework.beans.factory.annotation.Autowired


@GraphQLName("FoundationIcdCode")
data class GraphQLFoundationIcdCode(val url: String) {
    val code: String =
        Regex("""\d{5,}""").find(url)?.value ?: throw IllegalArgumentException("the url must contain a code")

    suspend fun linearizationEntity(@GraphQLIgnore @Autowired icdFetcher: IcdFetcher): GraphQLIcdLinearizationEntity =
        icdFetcher.linearizationEntity(this)
}

