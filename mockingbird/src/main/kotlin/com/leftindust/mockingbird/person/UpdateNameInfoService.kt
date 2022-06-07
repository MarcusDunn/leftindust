package com.leftindust.mockingbird.person

interface UpdateNameInfoService {
    suspend fun updateNameInfo(updateNameInfo: UpdateNameInfo, nameInfo: NameInfo)
}
