package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.group.GroupRepository
import com.leftindust.mockingbird.person.CreateNameInfoService
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class CreateMediqUserServiceImpl(
    private val mediqUserRepository: MediqUserRepository,
    private val createNameInfoService: CreateNameInfoService,
    private val groupRepository: GroupRepository,
) : CreateMediqUserService {

    override suspend fun addUser(user: CreateMediqUser): MediqUser? {
        val newUser = MediqUser(
            uniqueId = user.uid.value,
            group = user.group?.let { mediqGroupId ->
                groupRepository.findByIdOrNull(mediqGroupId.value)
                    ?: return null.also { logger.warn { "could not find a group with id [${mediqGroupId.value}] when creating $user." } }
            },
            nameInfoEntity = createNameInfoService.createNameInfo(user.nameInfo)
        )

        return mediqUserRepository.save(newUser)
    }
}