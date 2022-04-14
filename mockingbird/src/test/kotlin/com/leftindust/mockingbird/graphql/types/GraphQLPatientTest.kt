package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*


internal class GraphQLPatientTest {

    @Test
    fun user() {

        val gqlPatient = EntityStore.graphQLPatient("GraphQLPatientTest.user")

        val expected = EntityStore.user("GraphQLPatientTest.user")
            .apply { group?.id = UUID.nameUUIDFromBytes("dawg".toByteArray()) }

        val userDao = mockk<UserDao> {
            every { findPatientUser(gqlPatient.pid, any()) } returns expected
        }

        val result = runBlocking { gqlPatient.user(userDao, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(GraphQLUser(expected), result)
    }
}