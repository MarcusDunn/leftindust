package com.leftindust.mockingbird.group

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_GROUP')")
interface CreateGroupService {
    suspend fun addGroup(group: CreateGroupDto): MediqGroup
}