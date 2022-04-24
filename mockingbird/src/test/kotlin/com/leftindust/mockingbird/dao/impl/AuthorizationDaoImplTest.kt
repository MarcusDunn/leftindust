package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.AccessControlList
import com.leftindust.mockingbird.auth.AuthorizationDaoImpl
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.auth.HibernateAclRepository
import com.leftindust.mockingbird.user.HibernateUserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AuthorizationDaoImplTest {
    private val aclRepository = mockk<HibernateAclRepository>()
    private val userRepository = mockk<HibernateUserRepository>()

    @Test
    fun getRolesForUserByUid() {
        val acl = mockk<AccessControlList>()

        val mediqUser = mockk<MediqUser>("marcus") {
            every { group } returns null
        }

        every { userRepository.findByUniqueId("uid") } returns mediqUser
        every { aclRepository.findAllByMediqUser(mediqUser) } returns listOf(acl)

        val authorizationDaoImpl = AuthorizationDaoImpl(aclRepository, userRepository)
        val result = runBlocking {
            withContext(Dispatchers.IO) {
                authorizationDaoImpl.getRolesForUserByUid("uid")
            } }
        assertEquals(listOf(acl), result)
    }
}