package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.extensions.doThenNull
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
    private val logger = KotlinLogging.logger { }

    suspend fun groupsById(groupIds: Flow<MediqGroupDto.MediqGroupId>): Flow<MediqGroupDto?> {
        return groupIds.map { it to readGroupService.getGroupById(it) }
            .map {
                it.second?.let { mediqGroupToMediqGroupDto.convert(it) }
                    ?: doThenNull { logger.debug { "returning a null element from groupsById for ${it.first}" } }
            }
    }
}