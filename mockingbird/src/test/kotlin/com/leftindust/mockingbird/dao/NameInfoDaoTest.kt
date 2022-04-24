package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.NameInfoDao
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.persistence.EntityNotFoundException

class NameInfoDaoTest {

    @Nested
    inner class FoundEntity {
        private val nameInfo = mockk<NameInfo>()
        private val nameInfoDao = object : NameInfoDao {
            override fun findByUniqueId(uid: String, requester: MediqToken): NameInfo {
                return nameInfo
            }
        }

        @Test
        internal fun `test returns on get`() {
            assertEquals(nameInfo, nameInfoDao.getByUniqueId("uid", mockk()))
        }

        @Test
        internal fun `check returns on find`(): Unit = runBlocking {
            assertEquals(nameInfo, nameInfoDao.findByUniqueId("uid", mockk()))
        }
    }

    @Nested
    inner class EntityNotFound {
        private val nameInfoDao = object : NameInfoDao {
            override fun findByUniqueId(uid: String, requester: MediqToken): NameInfo? = null
        }

        @Test
        internal fun `test throws on get`(): Unit = runBlocking {
            assertThrows<EntityNotFoundException> {
                nameInfoDao.getByUniqueId("uid", mockk())
            }
        }

        @Test
        internal fun `check returns null on find`(): Unit = runBlocking {
            assertEquals(null, nameInfoDao.findByUniqueId("uid", mockk()))
        }
    }
}