package com.leftindust.mockingbird.graphql.mutations

import com.leftindust.mockingbird.user.UserDao
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.UserMutation
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

        val mockkMediqUserDto = MediqUserDto(mockkUser)

        every { userDao.addUser(any(), any()) } returns mockkUser

        val userMutation = UserMutation(userDao)

        val result = runBlocking { userMutation.addUser(mockk(), MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(mockkMediqUserDto, result)
    }
}