package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.group.MediqGroup
import com.leftindust.mockingbird.group.HibernateGroupRepository
import com.leftindust.mockingbird.user.GraphQLUserGroup
import com.leftindust.mockingbird.group.GraphQLGroupInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.group.GroupDaoImpl
import com.leftindust.mockingbird.util.unit.LenientAuthorizerUnitTest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Pageable
import java.util.*

internal class GroupDaoImplTest : LenientAuthorizerUnitTest() {

    @Test
    fun `check addGroup success`() {
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { save(any()) } returns mediqGroup
        }
        val groupDaoImpl = GroupDaoImpl(authorizer, groupRepository)
        val result = groupDaoImpl.addGroup(GraphQLGroupInput("doctors"), mockk())
        assertEquals(mediqGroup, result)
    }

    @Test
    fun `check getGroupById success`() {
        val mediqGroup = mockk<MediqGroup>()
        val groupRepository = mockk<HibernateGroupRepository> {
            every { getById(any()) } returns mediqGroup
        }
        val groupDaoImpl = GroupDaoImpl(authorizer, groupRepository)
        val result = groupDaoImpl.getGroupById(GraphQLUserGroup.ID(UUID.randomUUID()), mockk())
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
        val groupDaoImpl = GroupDaoImpl(authorizer, groupRepository)
        val result = groupDaoImpl.getRange(GraphQLRangeInput(0, 10), mockk())
        assertEquals(listOf(mediqGroup), result)
    }
}