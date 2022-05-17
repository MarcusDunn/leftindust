package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.user.MediqGroupDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class GroupMutationController(
    private val groupService: CreateGroupService,
    private val mediqGroupToMediqGroupDto: InfallibleConverter<MediqGroup, MediqGroupDto>,
) {
    @MutationMapping
    suspend fun addGroup(@Argument group: CreateGroupDto): MediqGroupDto {
        val newGroup = groupService.addGroup(group)
        return mediqGroupToMediqGroupDto.convert(newGroup)
    }
}