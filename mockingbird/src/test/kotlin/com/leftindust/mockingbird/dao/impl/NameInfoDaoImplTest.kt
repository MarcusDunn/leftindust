package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.person.NameInfoDao
import com.leftindust.mockingbird.person.NameInfoDaoImpl
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.user.HibernateUserRepository
import com.leftindust.mockingbird.util.unit.LenientAuthorizerUnitTest
import com.leftindust.mockingbird.util.unit.StrictAuthorizerUnitTest
import io.mockk.every
import io.mockk.mockk
import javax.persistence.EntityNotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NameInfoDaoImplTest {

    @Nested
    inner class Authorized : LenientAuthorizerUnitTest() {

        @Nested
        inner class SuccessfullyFindUserByUniqueId {
            private val mediqUser = mockk<MediqUser>(relaxed = true)
            private val hibernateUserRepository = mockk<HibernateUserRepository> {
                every { findByUniqueId("id") } returns mediqUser
            }

            @Test
            fun `check getByUniqueId success`() {
                val nameInfoDao: NameInfoDao = NameInfoDaoImpl(hibernateUserRepository, authorizer)
                val result = nameInfoDao.getByUniqueId("id", mockk())
                assertEquals(mediqUser.nameInfo, result)
            }

            @Test
            fun `check findByUniqueId success`() {
                val nameInfoDao = NameInfoDaoImpl(hibernateUserRepository, authorizer)
                val result = nameInfoDao.findByUniqueId("id", mockk())
                assertEquals(mediqUser.nameInfo, result)
            }
        }

        @Nested
        inner class FailFindUserByUniqueId {
            private val hibernateUserRepository = mockk<HibernateUserRepository> {
                every { findByUniqueId("id") } returns null
            }
            private val nameInfoDao: NameInfoDao = NameInfoDaoImpl(hibernateUserRepository, authorizer)

            @Test
            fun `check getByUniqueId failure`() {
                assertThrows<EntityNotFoundException> {
                    nameInfoDao.getByUniqueId("id", mockk())
                }
            }

            @Test
            fun `check findByUniqueId success`() {
                val result = nameInfoDao.findByUniqueId("id", mockk())
                assertEquals(null, result)
            }
        }
    }

    @Nested
    inner class Unauthorized : StrictAuthorizerUnitTest() {

        @Nested
        inner class SuccessfullyFindUserByUniqueId {
            private val mediqUser = mockk<MediqUser>()
            private val hibernateUserRepository = mockk<HibernateUserRepository> {
                every { findByUniqueId("id") } returns mediqUser
            }
            private val nameInfoDao: NameInfoDao = NameInfoDaoImpl(hibernateUserRepository, authorizer)

            @Test
            fun `check getByUniqueId unauthorized`(): Unit = runBlocking {
                assertThrows<NotAuthorizedException> {
                    nameInfoDao.getByUniqueId("id", mockk())
                }
            }

            @Test
            fun `check findByUniqueId unauthorized`(): Unit = runBlocking {
                assertThrows<NotAuthorizedException> {
                    nameInfoDao.findByUniqueId("id", mockk())
                }
            }
        }

        @Nested
        inner class FailFindUserByUniqueId {
            private val hibernateUserRepository = mockk<HibernateUserRepository> {
                every { findByUniqueId("id") } returns null
            }
            private val nameInfoDao: NameInfoDao = NameInfoDaoImpl(hibernateUserRepository, authorizer)

            @Test
            fun `check getByUniqueId failure`(): Unit = runBlocking {
                assertThrows<NotAuthorizedException> {
                    nameInfoDao.getByUniqueId("id", mockk())
                }
            }

            @Test
            fun `check findByUniqueId failure`(): Unit = runBlocking {
                assertThrows<NotAuthorizedException> {
                    nameInfoDao.findByUniqueId("id", mockk())
                }
            }
        }
    }
}