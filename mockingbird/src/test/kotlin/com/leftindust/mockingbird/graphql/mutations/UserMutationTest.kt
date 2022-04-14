package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.dao.entity.MediqUser
import com.leftindust.mockingbird.graphql.types.GraphQLUser
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserMutationTest {
    private val userDao = mockk<UserDao>()


    @Test
    fun addUser() {
        val userId = UUID.randomUUID()

        val mockkUser = mockk<MediqUser>(relaxed = true) {
            every { group } returns mockk(relaxed = true) {
                every { id } returns userId
            }
        }

        val mockkGraphQLUser = GraphQLUser(mockkUser)

        every { userDao.addUser(any(), any()) } returns mockkUser

        val userMutation = UserMutation(userDao)

        val result = runBlocking { userMutation.addUser(mockk(), MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(mockkGraphQLUser, result)
    }
}