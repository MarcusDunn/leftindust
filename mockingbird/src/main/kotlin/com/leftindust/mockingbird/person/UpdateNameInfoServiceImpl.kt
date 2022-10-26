package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressRepository
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.address.UpdateAddress
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
        updateNameInfo.firstName.applyUpdatable(nameInfoEntity, nameInfoEntity::firstName, logger)
        updateNameInfo.middleName.applyDeletable(nameInfoEntity, nameInfoEntity::middleName, logger)
        updateNameInfo.lastName.applyUpdatable(nameInfoEntity, nameInfoEntity::lastName, logger)
        nameInfoRepository.save(nameInfoEntity)
    }
}