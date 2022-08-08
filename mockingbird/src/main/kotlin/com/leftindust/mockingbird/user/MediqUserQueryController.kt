package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserQueryController(
    private val readMediqUserService: ReadMediqUserService,
    private val mediqUserToMediqUserDtoConverter: InfallibleConverter<MediqUser, MediqUserDto>,
) {
    @QueryMapping("userByUserUniqueId")
    suspend fun userByUserUniqueId(@Argument("uniqueId") uniqueId: String): MediqUserDto? {
        val mediqUser = readMediqUserService.getByUserUid(uniqueId)
            ?: return null
        return mediqUserToMediqUserDtoConverter.convert(mediqUser)
    }
}

