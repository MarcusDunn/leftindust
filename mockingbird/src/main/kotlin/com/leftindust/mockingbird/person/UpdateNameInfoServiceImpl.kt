package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import javax.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateNameInfoServiceImpl(
    private val nameInfoRepository: NameInfoRepository,
) : UpdateNameInfoService {
    private val logger = LoggerFactory.getLogger(UpdateNameInfoServiceImpl::class.java)

    override suspend fun updateNameInfo(updateNameInfo: UpdateNameInfo, nameInfo: NameInfo) {
        updateFirstName(updateNameInfo.firstName, nameInfo)
        updateMiddleName(updateNameInfo.middleName, nameInfo)
        updateLastName(updateNameInfo.lastName, nameInfo)
        nameInfoRepository.save(nameInfo)
    }

    private fun updateLastName(lastName: Updatable<String>, nameInfo: NameInfo) {
        when (lastName) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $nameInfo ${NameInfo::lastName.name}", "Update was $nameInfo").toString())
            }
            is Updatable.Update -> {
                logger.trace(LogMessage("Updated $nameInfo ${NameInfo::lastName.name} to ${lastName.value}", "Update was $nameInfo").toString())
                nameInfo.lastName = lastName.value
            }
        }
    }

    private fun updateMiddleName(middleName: Deletable<String>, nameInfo: NameInfo) {
        when (middleName) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $nameInfo ${NameInfo::middleName.name}", "Update was $nameInfo").toString())
            }
            is Updatable.Update -> {
                logger.trace(LogMessage("Updated $nameInfo ${NameInfo::middleName.name} to ${middleName.value}", "Update was $nameInfo").toString())
                nameInfo.middleName = middleName.value
            }
            is Deletable.Delete -> {
                logger.trace(LogMessage("Removed $nameInfo ${NameInfo::middleName.name}", "Update was $nameInfo").toString())
            }
        }
    }

    private fun updateFirstName(firstName: Updatable<String>, nameInfo: NameInfo) {
        when (firstName) {
            is Updatable.Ignore -> {
                logger.trace(LogMessage("Did not update $nameInfo ${NameInfo::firstName.name}", "Update was $nameInfo").toString())
            }
            is Updatable.Update -> {
                logger.trace(LogMessage("Updated $nameInfo ${NameInfo::firstName.name} to ${firstName.value}", "Update was $nameInfo").toString())
                nameInfo.firstName = firstName.value
            }
        }
    }
}