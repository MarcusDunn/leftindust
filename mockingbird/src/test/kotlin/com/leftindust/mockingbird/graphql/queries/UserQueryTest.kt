package com.leftindust.mockingbird.graphql.queries

import com.expediagroup.graphql.generator.scalars.ID
import com.google.firebase.auth.ExportedUserRecord
import com.leftindust.mockingbird.user.UserDao
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.UserFetcher
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.user.UserQuery
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserQueryTest {
    private val userDao = mockk<UserDao>()
    private val firebaseFetcher = mockk<UserFetcher>()

    @Test
    fun user() {
        val user = mockk<MediqUser> {
            every { uniqueId } returns "uid"
            every { group } returns null
        }
        every { userDao.findUserByUid("uid", any()) } returns user
        val userQuery = UserQuery(userDao, firebaseFetcher)

        val result = runBlocking { userQuery.user(ID("uid"), MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals(MediqUserDto(user), result)
    }

    @Test
    fun users() {
        val user = mockk<MediqUser> {
            every { uniqueId } returns "uid"
            every { group } returns null
        }
        every { userDao.getUsers(any(), any()) } returns listOf(user)
        val userQuery = UserQuery(userDao, firebaseFetcher)
        val result = runBlocking { userQuery.users(RangeDto(0, 3), dataFetchingEnvironment = MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals(listOf(MediqUserDto(user)), result)
    }

    @Test
    fun `firebaseUsers when not all users are registered`() {
        val userRecords = (0 until 5).map {
            mockk<ExportedUserRecord>(relaxed = true) {
                every { uid } returns "uid$it"
            }
        }

        every { userDao.findUserByUid("uid0", any()) } returns mockk()
        every { userDao.findUserByUid("uid1", any()) } returns mockk()
        every { userDao.findUserByUid("uid2", any()) } returns mockk()
        every { userDao.findUserByUid("uid3", any()) } returns null
        every { userDao.findUserByUid("uid4", any()) } returns mockk()

        every { firebaseFetcher.getUsers(any()) } returns mockk {
            every { iterator() } returns mockk {
                every { hasNext() } returnsMany listOf(true, true, true, true, true, false)
                every { next() } returnsMany userRecords
            }
        }

        val userQuery = UserQuery(userDao, firebaseFetcher)

        val result = runBlocking { userQuery.firebaseUsers(RangeDto(0, 4), true, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(4, result.size)
    }
}