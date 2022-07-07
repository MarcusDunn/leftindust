package com.leftindust.mockingbird.graphql.queries

import com.ninjasquad.springmockk.MockkBean
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

internal class HealthCheckQueryControllerUnitTest {
    private val healthCheckQueryController = HealthCheckQueryController()

    @Test
    internal fun `check is alive returns true`() {
        assertThat(healthCheckQueryController.mockingbirdIsAlive(), equalTo(true))
    }
}

@GraphQlTest(controllers = [HealthCheckQueryController::class])
internal class HealthCheckQueryControllerWebTest(
    @Autowired private val graphqlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @Test
    internal fun `test mockingbirdIsAlive`() {
        //language=graphql
        val query = """query { mockingbirdIsAlive }"""
        graphqlTester
            .document(query)
            .execute()
            .errors()
            .satisfy { assertThat(it, hasSize(0)) }
            .path("data.mockingbirdIsAlive")
            .entity(Boolean::class.java)
            .satisfies { assertThat(it, equalTo(true)) }
    }
}