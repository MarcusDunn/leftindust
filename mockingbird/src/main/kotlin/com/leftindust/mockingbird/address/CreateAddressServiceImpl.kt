package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.CountryState
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateAddressServiceImpl(private val addressRepository: AddressRepository) : CreateAddressService {
    override suspend fun createAddress(createAddress: CreateAddress): AddressEntity {
        val addressEntity = AddressEntity(
            type = createAddress.addressType,
            address = createAddress.address,
            city = createAddress.city,
            countryState = CountryState(createAddress.country, createAddress.province),
            postalCode = createAddress.postalCode,
        )
        return addressRepository.save(addressEntity)
    }
}