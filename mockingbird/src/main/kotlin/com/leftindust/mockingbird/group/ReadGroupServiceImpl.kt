package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.user.MediqGroupDto
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ReadGroupServiceImpl(
    private val mediqGroupRepository: HibernateGroupRepository,
) : ReadGroupService {
    override suspend fun getGroupById(gid: MediqGroupDto.MediqGroupId): MediqGroup? {
        return mediqGroupRepository.findById(gid.value).orElse(null)
    }
}