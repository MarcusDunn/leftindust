package com.leftindust.mockingbird.auth.impl

import com.leftindust.mockingbird.auth.AuthorizerImpl
import com.leftindust.mockingbird.auth.AuthorizationDao
import com.leftindust.mockingbird.extensions.Authorization
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class AuthorizerImplTest {

    @MockK
    private lateinit var authorizationDao: AuthorizationDao

    @Test
    fun `get authorization when user has subset of required permissions`() {
        every { authorizationDao.getRolesForUserByUid("marcus") } returns listOf(
            mockk {
                every { action.isSuperset(any()) } returns false
            }
        )
        every { authorizationDao.isAdmin(any()) } returns false
        every { authorizationDao.isPatient(any()) } returns false

        val authorizer = AuthorizerImpl(authorizationDao)

        val actual = runBlocking {
            authorizer.getAuthorization(
                action = mockk(),
                user = mockk("marcus user") { every { uid } returns "marcus" })
        }

        assertEquals(Authorization.Denied, actual)

        verify { authorizationDao.getRolesForUserByUid("marcus") }
    }

    @Test
    fun `get authorization when user has superset of required permissions`() {
        every { authorizationDao.isAdmin(any()) } returns false
        every { authorizationDao.isPatient(any()) } returns false


        every { authorizationDao.getRolesForUserByUid("marcus") } returns listOf(
            mockk("superset") {
                every { action.isSuperset(any()) } returns true
            }
        )

        val authorizer = AuthorizerImpl(authorizationDao)

        val actual = runBlocking {
            authorizer.getAuthorization(
                action = mockk("subset"),
                user = mockk("marcus user") { every { uid } returns "marcus" })
        }

        assertEquals(Authorization.Allowed, actual)

        verify { authorizationDao.getRolesForUserByUid("marcus") }
    }

    @Test
    fun getAuthorizationForAdmin() {
        every { authorizationDao.isAdmin(any()) } returns true
        val authorizer = AuthorizerImpl(authorizationDao)
        val actual = runBlocking {
            authorizer.getAuthorization(
                action = mockk("subset"),
                user = mockk("marcus user") { every { uid } returns "marcus" })
        }
        assertEquals(Authorization.Allowed, actual)
    }
}