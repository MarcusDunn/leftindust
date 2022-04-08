package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*


internal class GraphQLPatientTest {

    @Test
    fun user() {
        val authContext = mockk<GraphQLAuthContext> {
            every { mediqAuthToken } returns mockk()
        }

        val gqlPatient = EntityStore.graphQLPatient("GraphQLPatientTest.user", authContext)

        val expected = EntityStore.user("GraphQLPatientTest.user")
            .apply { group?.id = UUID.nameUUIDFromBytes("dawg".toByteArray()) }

        val userDao = mockk<UserDao> {
            every { findPatientUser(gqlPatient.pid, any()) } returns expected
        }

        val result = runBlocking { gqlPatient.user(userDao) }

        assertEquals(GraphQLUser(expected, authContext), result)
    }
}