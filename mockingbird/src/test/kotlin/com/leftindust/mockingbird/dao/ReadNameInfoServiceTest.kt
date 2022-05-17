package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.ReadNameInfoService
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.persistence.EntityNotFoundException

class ReadNameInfoServiceTest {

    @Nested
    inner class FoundEntity {
        private val nameInfo = mockk<NameInfo>()
        private val readNameInfoService = object : ReadNameInfoService {
            override fun findByUniqueId(uid: String, requester: MediqToken): NameInfo {
                return nameInfo
            }
        }

        @Test
        internal fun `test returns on get`() {
            assertEquals(nameInfo, readNameInfoService.getByUniqueId("uid", mockk()))
        }

        @Test
        internal fun `check returns on find`(): Unit = runBlocking {
            assertEquals(nameInfo, readNameInfoService.findByUniqueId("uid", mockk()))
        }
    }

    @Nested
    inner class EntityNotFound {
        private val readNameInfoService = object : ReadNameInfoService {
            override fun findByUniqueId(uid: String, requester: MediqToken): NameInfo? = null
        }

        @Test
        internal fun `test throws on get`(): Unit = runBlocking {
            assertThrows<EntityNotFoundException> {
                readNameInfoService.getByUniqueId("uid", mockk())
            }
        }

        @Test
        internal fun `check returns null on find`(): Unit = runBlocking {
            assertEquals(null, readNameInfoService.findByUniqueId("uid", mockk()))
        }
    }
}