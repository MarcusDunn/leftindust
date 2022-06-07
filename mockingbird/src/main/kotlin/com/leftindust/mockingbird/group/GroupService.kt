package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.user.MediqGroupDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('READ_GROUP')")
interface ReadGroupService {
    suspend fun getGroupById(gid: MediqGroupDto.MediqGroupId): MediqGroup?
}

