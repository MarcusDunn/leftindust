package com.leftindust.mockingbird.util.unit

import com.leftindust.mockingbird.auth.CONTEXT_MAP_KEY
import com.leftindust.mockingbird.auth.MediqToken
import graphql.GraphQLContext
import graphql.schema.DataFetchingEnvironmentImpl
import graphql.schema.DelegatingDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk

class MockDataFetchingEnvironment(
    graphQLContext: GraphQLContext = GraphQLContext.newContext().build()
) : DelegatingDataFetchingEnvironment(
    DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
        .graphQLContext(graphQLContext)
        .build()
) {
    companion object {
        val withDummyMediqToken = MockDataFetchingEnvironment(
            GraphQLContext.newContext().of(MediqToken.CONTEXT_MAP_KEY, mockk<MediqToken>()).build()
        )
        val withVerifiedMediqToken =
            MockDataFetchingEnvironment(GraphQLContext.newContext().of(MediqToken.CONTEXT_MAP_KEY, mockk<MediqToken>() {
                every { isVerified() } returns true
            }).build())

    }
}