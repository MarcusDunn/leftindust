package com.leftindust.mockingbird.graphql.queries

import com.ninjasquad.springmockk.MockkBean
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
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
internal class HealthCheckQueryControllerWebTestTest(
    @Autowired private val graphqlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @Test
    internal fun `test is alive has no errors`() {
        val response = graphqlTester
            .document("""query { mockingbirdIsAlive }""")
            .execute()
        println(response)
    }
}