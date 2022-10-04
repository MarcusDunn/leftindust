package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.applyDeletable
import com.leftindust.mockingbird.graphql.types.applyUpdatable
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateNameInfoServiceImpl(
    private val nameInfoRepository: NameInfoRepository,
) : UpdateNameInfoService {
    private val logger = KotlinLogging.logger { }

    override suspend fun updateNameInfo(updateNameInfo: UpdateNameInfo, nameInfoEntity: NameInfoEntity) {
        updateFirstName(updateNameInfo.firstName, nameInfoEntity)
        updateMiddleName(updateNameInfo.middleName, nameInfoEntity)
        updateLastName(updateNameInfo.lastName, nameInfoEntity)
        nameInfoRepository.save(nameInfoEntity)
    }

    private fun updateLastName(lastName: Updatable<String>, nameInfoEntity: NameInfoEntity) {
        lastName.applyUpdatable(nameInfoEntity, nameInfoEntity::lastName, logger)
    }

    private fun updateMiddleName(middleName: Deletable<String>, nameInfoEntity: NameInfoEntity) {
        middleName.applyDeletable(nameInfoEntity, nameInfoEntity::middleName, logger)
    }

    private fun updateFirstName(firstName: Updatable<String>, nameInfoEntity: NameInfoEntity) {
        firstName.applyUpdatable(nameInfoEntity, nameInfoEntity::firstName, logger)
    }
}