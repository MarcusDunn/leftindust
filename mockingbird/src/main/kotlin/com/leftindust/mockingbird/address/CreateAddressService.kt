package com.leftindust.mockingbird.address

interface CreateAddressService {
    suspend fun createAddress(createAddress: CreateAddress): Address
}