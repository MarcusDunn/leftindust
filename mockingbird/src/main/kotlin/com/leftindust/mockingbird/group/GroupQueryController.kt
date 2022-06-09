package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.user.MediqGroupDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class GroupQueryController(
    private val readGroupService: ReadGroupService,
    private val mediqGroupToMediqGroupDto: InfallibleConverter<MediqGroup, MediqGroupDto>,
) {
    suspend fun groupsById(groupIds: Flow<MediqGroupDto.MediqGroupId>): Flow<MediqGroupDto?> {
        return groupIds
            .map { readGroupService.getGroupById(it) }
            .map { it?.let { group -> mediqGroupToMediqGroupDto.convert(group) } }
    }
}