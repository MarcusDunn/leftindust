package com.leftindust.mockingbird.group

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateGroupServiceImpl : CreateGroupService {
    override suspend fun addGroup(group: CreateGroupDto): MediqGroup {
        TODO("Not yet implemented")
    }
}