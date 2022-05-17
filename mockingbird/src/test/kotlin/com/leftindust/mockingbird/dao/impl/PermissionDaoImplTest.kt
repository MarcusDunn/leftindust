package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Crud.CREATE
import com.leftindust.mockingbird.auth.Tables.User
import com.leftindust.mockingbird.auth.AccessControlList
import com.leftindust.mockingbird.auth.PermissionDaoImpl
import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.auth.HibernateAclRepository
import com.leftindust.mockingbird.group.HibernateGroupRepository
import com.leftindust.mockingbird.user.HibernateUserRepository
import com.leftindust.mockingbird.user.MediqGroupDto
import com.leftindust.mockingbird.auth.GraphQLPermissionInput
import com.leftindust.mockingbird.util.unit.LenientAuthorizerUnitTest
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PermissionDaoImplTest : LenientAuthorizerUnitTest() {

    @Test
    fun `check addUserPermission success`() {
        val uuid = UUID.randomUUID()
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { getById(uuid) } returns mediqGroup
        }
        val accessControlList = mockk<AccessControlList>()
        val aclRepository = mockk<HibernateAclRepository> {
            every { save(any()) } returns accessControlList
        }
        val mediqUser = mockk<MediqUser>()
        val userRepository = mockk<HibernateUserRepository> {
            every { getByUniqueId("uuid") } returns mediqUser
        }
        val permissionDao = PermissionDaoImpl(groupRepository, aclRepository, userRepository, authorizer)
        val result = permissionDao.addUserPermission("uuid", GraphQLPermissionInput(User, CREATE), mockk())
        assertEquals(accessControlList, result)
    }

    @Test
    fun `check addGroupPermission success`() {
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { getById(any()) } returns mediqGroup
        }
        val accessControlList = mockk<AccessControlList>()
        val aclRepository = mockk<HibernateAclRepository> {
            every { save(any()) } returns accessControlList
        }
        val uuid = UUID.randomUUID()
        val mockk = mockk<MediqUser>()
        val userRepository = mockk<HibernateUserRepository> {
            every { getById(uuid) } returns mockk
        }
        val permissionDao = PermissionDaoImpl(groupRepository, aclRepository, userRepository, authorizer)
        val result =
            permissionDao.addGroupPermission(MediqGroupDto.MediqGroupId(uuid), GraphQLPermissionInput(User, CREATE), mockk())
        assertEquals(accessControlList, result)
    }
}