package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.group.HibernateGroupRepository
import com.leftindust.mockingbird.user.MediqGroupDto
import com.leftindust.mockingbird.group.CreateGroupDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.util.unit.LenientAuthorizerUnitTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Pageable
import java.util.*

internal class GroupServiceImplTest : LenientAuthorizerUnitTest() {

    @Test
    fun `check addGroup success`() {
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { save(any()) } returns mediqGroup
        }
        val groupDaoImpl = GroupServiceImpl(authorizer, groupRepository)
        val result = groupDaoImpl.addGroup(CreateGroupDto("doctors"), mockk())
        assertEquals(mediqGroup, result)
    }

    @Test
    fun `check getGroupById success`() {
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { getById(any()) } returns mediqGroup
        }
        val groupDaoImpl = GroupServiceImpl(authorizer, groupRepository)
        val result = groupDaoImpl.getGroupById(MediqGroupDto.MediqGroupId(UUID.randomUUID()), mockk())
        assertEquals(mediqGroup, result)
    }

    @Test
    fun `check getRange success`() {
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { findAll(any<Pageable>()) } returns mockk {
                every { content } returns listOf(mediqGroup)
            }
        }
        val groupDaoImpl = GroupServiceImpl(authorizer, groupRepository)
        val result = groupDaoImpl.getRange(RangeDto(0, 10), mockk())
        assertEquals(listOf(mediqGroup), result)
    }
}