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

    override suspend fun updateNameInfo(updateNameInfo: UpdateNameInfo, nameInfo: NameInfo) {
        updateFirstName(updateNameInfo.firstName, nameInfo)
        updateMiddleName(updateNameInfo.middleName, nameInfo)
        updateLastName(updateNameInfo.lastName, nameInfo)
        nameInfoRepository.save(nameInfo)
    }

    private fun updateLastName(lastName: Updatable<String>, nameInfo: NameInfo) {
        lastName.applyUpdatable(nameInfo, nameInfo::lastName, logger)
    }

    private fun updateMiddleName(middleName: Deletable<String>, nameInfo: NameInfo) {
        middleName.applyDeletable(nameInfo, nameInfo::middleName, logger)
    }

    private fun updateFirstName(firstName: Updatable<String>, nameInfo: NameInfo) {
        firstName.applyUpdatable(nameInfo, nameInfo::firstName, logger)
    }
}