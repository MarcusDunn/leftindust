package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.address.AddressRepository
import com.leftindust.mockingbird.country.CountryState
import com.leftindust.mockingbird.graphql.types.applyDeletable
import com.leftindust.mockingbird.graphql.types.applyUpdatable
import com.leftindust.mockingbird.patient.UpdateAddress
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

interface UpdateAddressService {
    suspend fun updateAddress(updateAddress: UpdateAddress, addressEntity: Address)
}

@Service
@Transactional
class UpdateAddressServiceImpl(
    private val addressRepository: AddressRepository,
) : UpdateAddressService {
    private val logger = KotlinLogging.logger { }

    override suspend fun updateAddress(updateAddress: UpdateAddress, addressEntity: Address) {
//        val countryState = CountryState(updateAddress.country, updateAddress.province.)
        updateAddress.addressType.applyDeletable(addressEntity, addressEntity::type, logger)
        updateAddress.address.applyUpdatable(addressEntity, addressEntity::address, logger)
        updateAddress.city.applyUpdatable(addressEntity, addressEntity::city, logger)
//        updateAddress.country.applyUpdatable(addressEntity, addressEntity::countryState, logger)
        updateAddress.postalCode.applyUpdatable(addressEntity, addressEntity::city, logger)
        addressRepository.save(addressEntity)
    }
}