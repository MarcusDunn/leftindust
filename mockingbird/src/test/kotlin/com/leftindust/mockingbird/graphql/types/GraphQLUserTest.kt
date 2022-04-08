package com.leftindust.mockingbird.graphql.types

import com.google.firebase.auth.UserRecord
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.AuthorizationDao
import com.leftindust.mockingbird.dao.NameInfoDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.dao.entity.Action
import com.leftindust.mockingbird.dao.entity.NameInfo
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import com.leftindust.mockingbird.external.firebase.UserFetcher
import com.leftindust.mockingbird.graphql.types.input.GraphQLPermissionInput
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class GraphQLUserTest {

    @Test
    fun `isRegistered general success`() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val userDao = mockk<UserDao> {
            every { findUserByUid("uid", any()) } returns mockk()
        }

        val graphQLUser = runBlocking { GraphQLUser("uid", null, authContext).isRegistered(userDao) }
        assertEquals(true, graphQLUser)
    }

    @Test
    fun `isRegistered special success`() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val userDao = mockk<UserDao> {
            every { findUserByUid("uid", any()) } returns null
        }

        val graphQLUser = runBlocking { GraphQLUser("uid", null, authContext).isRegistered(userDao) }
        assertEquals(false, graphQLUser)
    }

    @Test
    fun `isRegistered general failure`() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }
        val userDao = mockk<UserDao> {
            every { findUserByUid("uid", any()) } throws NotAuthorizedException(
                authContext.mediqAuthToken,
                Crud.READ to Tables.User
            )
        }

        assertThrows<NotAuthorizedException> {
            runBlocking { GraphQLUser("uid", null, authContext).isRegistered(userDao) }
        }
    }

    @Test
    fun firebaseUserInfo() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val expected = mockk<UserRecord>(relaxed = true)

        val userFetcher = mockk<UserFetcher> {
            every { getUserInfo("uid", authContext.mediqAuthToken) } returns expected
        }
        val result = runBlocking { GraphQLUser("uid", null, authContext).firebaseUserInfo(userFetcher) }

        assertEquals(GraphQLFirebaseInfo(expected), result)
    }

    @Test
    fun permissions() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val authorizationDao = mockk<AuthorizationDao> {
            every { getRolesForUserByUid("uid") } returns emptyList()
        }

        val result = runBlocking { GraphQLUser("uid", null, authContext).permissions(authorizationDao) }

        assertEquals(GraphQLPermissions(emptyList()), result)
    }

    @Test
    fun hasPermission() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val authorizationDao = mockk<AuthorizationDao> {
            every { getRolesForUserByUid("uid") } returns listOf(mockk {
                every { action } returns Action(Crud.UPDATE to Tables.Patient)
            })
        }

        val result = runBlocking {
            GraphQLUser("uid", null, authContext).hasPermission(
                authorizationDao,
                GraphQLPermissionInput(referencedTableName = Tables.Patient, permissionType = Crud.UPDATE)
            )
        }

        assertEquals(true, result)
    }

    @Test
    fun patient() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val expectedPatient = EntityStore.patient("GraphqlUserTest.patient")
            .apply { id = UUID.nameUUIDFromBytes("GraphqlUserTest.patient".toByteArray()) }

        val mockkPatientDao = mockk<ReadPatientDao> {
            every { getByUser("uid", any()) } returns expectedPatient
        }

        val result = runBlocking { GraphQLUser("uid", null, authContext).patient(mockkPatientDao) }

        assertEquals(GraphQLPatient(expectedPatient, authContext), result)
    }

    @Test
    internal fun names() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val nameInfo = mockk<NameInfo>(relaxed = true)

        val nameInfoDao = mockk<NameInfoDao> {
            every { findByUniqueId("uid", any()) } returns nameInfo
        }

        val result = runBlocking { GraphQLUser("uid", null, authContext).name(nameInfoDao) }

        assertEquals(GraphQLNameInfo(nameInfo), result)
    }
}