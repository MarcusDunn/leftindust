package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.user.MediqUserDto

interface CreateNameInfoService {
    suspend fun createNameInfo(createNameInfo: CreateNameInfo): NameInfoEntity
}

interface ReadNameInfoService {
    fun getByUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): NameInfo?
}