package com.leftindust.mockingbird.util.integration

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.ContextFactory
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.extensions.Authorization
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach

abstract class NoAuthIntegrationTest : IntegrationTest() {
    @MockkBean
    private lateinit var contextFactory: ContextFactory

    @MockkBean
    private lateinit var authorizer: Authorizer

    @BeforeEach
    fun disableAuth() {
        coEvery { contextFactory.generateContext(any()) } returns GraphQLAuthContext(mockk {
            every { isVerified() } returns true
        }, mockk(relaxed = true))

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
    }
}
