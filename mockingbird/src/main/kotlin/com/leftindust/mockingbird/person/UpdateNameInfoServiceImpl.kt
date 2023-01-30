package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.types.applyDeletable
import com.leftindust.mockingbird.graphql.types.applyUpdatable
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateNameInfoServiceImpl(
    private val nameInfoRepository: NameInfoRepository,
) : UpdateNameInfoService {
    private val logger = KotlinLogging.logger { }

    override suspend fun updateNameInfo(updateNameInfo: UpdateNameInfo, nameInfoEntity: NameInfoEntity) {
        updateNameInfo.firstName.applyUpdatable(nameInfoEntity, nameInfoEntity::firstName, logger)
        updateNameInfo.middleName.applyDeletable(nameInfoEntity, nameInfoEntity::middleName, logger)
        updateNameInfo.lastName.applyUpdatable(nameInfoEntity, nameInfoEntity::lastName, logger)
        nameInfoRepository.save(nameInfoEntity)
    }
}