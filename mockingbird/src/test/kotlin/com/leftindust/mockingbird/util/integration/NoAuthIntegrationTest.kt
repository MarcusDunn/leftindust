package com.leftindust.mockingbird.util.integration

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.CONTEXT_MAP_KEY
import com.leftindust.mockingbird.auth.ContextFactory
import com.leftindust.mockingbird.auth.MediqToken
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
        coEvery { contextFactory.generateContextMap(any()) } returns mapOf(
            MediqToken.CONTEXT_MAP_KEY to mockk<MediqToken> {
                every { isVerified() } returns true
            }
        )

        coEvery {
            @Suppress("DEPRECATION", "")
            contextFactory.generateContext(any())
        } returns null

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
    }
}
