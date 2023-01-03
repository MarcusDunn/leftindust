package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.user.MediqGroupDto
import com.leftindust.mockingbird.user.toMediqGroupDto
import dev.forkhandles.result4k.onFailure
import org.springframework.stereotype.Component

@Component
class GroupQueryController(
    private val readGroupService: ReadGroupService,
) {
    suspend fun groupsById(groupIds: List<MediqGroupDto.MediqGroupId>): List<MediqGroupDto?> {
        return groupIds
            .map { readGroupService.getGroupById(it) }
            .map { it?.let { group -> group.toMediqGroupDto().onFailure { throw it.reason.toMockingbirdException() } } }
    }
}
