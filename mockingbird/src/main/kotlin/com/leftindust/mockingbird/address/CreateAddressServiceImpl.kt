package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.country.CountryState
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateAddressServiceImpl(private val addressRepository: AddressRepository) : CreateAddressService {
    override suspend fun createAddress(createAddress: CreateAddress): Address {
        val address = Address(
            type = createAddress.addressType,
            address = createAddress.address,
            city = createAddress.city,
            countryState = CountryState(createAddress.country, createAddress.province),
            postalCode = createAddress.postalCode,
        )
        return addressRepository.save(address)
    }
}