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
            every { headers() } returns mockk(relaxed = true) {
                every { firstHeader("Authorization") } returns "Bearer: 123456"
            }
        }

        val actual = runBlocking {
            contextFactory.generateContextMap(
                request = mockkRequest
            )[MediqToken.CONTEXT_MAP_KEY]
        }
        assertEquals(VerifiedFirebaseToken("123456"), actual)
    }
}