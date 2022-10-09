package com.leftindust.mockingbird.person

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateNameInfoServiceImpl(private val nameInfoRepository: NameInfoRepository) : CreateNameInfoService {
    override suspend fun createNameInfo(createNameInfo: CreateNameInfo): NameInfoEntity {
        val nameInfo = NameInfoEntity(createNameInfo.firstName, createNameInfo.lastName, createNameInfo.middleName)
        return nameInfoRepository.save(nameInfo)
    }
}