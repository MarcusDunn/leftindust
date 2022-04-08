package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.auth.impl.VerifiedFirebaseToken
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.server.ServerRequest

internal class ContextFactoryTest {
    private val contextFactory = ContextFactory()

    @Test
    fun generateContext() {
        val mockkRequest = mockk<ServerRequest> {
            every { method() } returns HttpMethod.POST
            every { headers() } returns mockk {
                every { firstHeader("mediq-auth-token") } returns "123456"
            }
        }

        val actual = runBlocking {
            contextFactory.generateContext(
                request = mockkRequest
            )
        }
        assertEquals(actual, GraphQLAuthContext(VerifiedFirebaseToken("123456"), mockkRequest))
    }
}