package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.NameInfoDto
import com.leftindust.mockingbird.person.ReadNameInfoService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserNameInfoQueryController(
    private val readNameInfoService: ReadNameInfoService,
    private val nameInfoToNameInfoDtoConverter: InfallibleConverter<NameInfo, NameInfoDto>,
) {
    @QueryMapping
    suspend fun name(mediqUser: MediqUserDto): NameInfoDto {
        val nameInfo = readNameInfoService.getByUniqueId(mediqUser.id)
            ?: throw NullSubQueryException(mediqUser, ReadNameInfoService::getByPatientId)
        return nameInfoToNameInfoDtoConverter.convert(nameInfo)
    }
}