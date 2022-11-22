package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.person.NameInfoDto
import com.leftindust.mockingbird.person.ReadNameInfoService
import com.leftindust.mockingbird.person.toNameInfoDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserNameInfoQueryController(
    private val readNameInfoService: ReadNameInfoService,
) {
    @QueryMapping
    suspend fun name(mediqUser: MediqUserDto): NameInfoDto {
        val nameInfo = readNameInfoService.getByUniqueId(mediqUser.id)
            ?: throw NullSubQueryException(mediqUser, ReadNameInfoService::getByUniqueId)
        return nameInfo.toNameInfoDto()
    }
}