package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.user.MediqGroupDto
import com.leftindust.mockingbird.user.toMediqGroupDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class GroupMutationController(
    private val groupService: CreateGroupService,
) {
    @MutationMapping
    suspend fun addGroup(@Argument group: CreateGroupDto): MediqGroupDto {
        val newGroup = groupService.addGroup(group)
        return newGroup.toMediqGroupDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}
