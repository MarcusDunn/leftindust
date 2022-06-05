package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
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
        when (lastName) {
            is Updatable.Ignore -> {
                logger.trace { "Did not update $nameInfo lastName" }
            }
            is Updatable.Update -> {
                logger.trace { "Updated $nameInfo lastName to ${lastName.value}" }
                nameInfo.lastName = lastName.value
            }
        }
    }

    private fun updateMiddleName(middleName: Deletable<String>, nameInfo: NameInfo) {
        when (middleName) {
            is Updatable.Ignore -> {
                logger.trace { "Did not update $nameInfo middleName" }
            }
            is Updatable.Update -> {
                logger.trace { "Updated $nameInfo middleName to ${middleName.value}" }
                nameInfo.middleName = middleName.value
            }
            is Deletable.Delete -> {
                logger.trace { "Removed $nameInfo middleName" }
            }
        }
    }

    private fun updateFirstName(firstName: Updatable<String>, nameInfo: NameInfo) {
        when (firstName) {
            is Updatable.Ignore -> {
                logger.trace { "Did not update $nameInfo firstName" }
            }
            is Updatable.Update -> {
                logger.trace { "Updated $nameInfo firstName to ${firstName.value}" }
                nameInfo.firstName = firstName.value
            }
        }
    }
}