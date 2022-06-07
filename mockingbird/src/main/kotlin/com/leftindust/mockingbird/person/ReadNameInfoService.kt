package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.user.MediqUserDto

interface CreateNameInfoService {
    suspend fun createNameInfo(createNameInfo: CreateNameInfo): NameInfo
}

interface ReadNameInfoService {
    fun getByUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): NameInfo?
}