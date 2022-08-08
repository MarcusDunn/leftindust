package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InfallibleConverter
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserMutationController(
    private val createMediqUserService: CreateMediqUserService,
    private val mediqUserToMediqUserDtoConverter: InfallibleConverter<MediqUser, MediqUserDto>,
    private val createMediqUserDtoToCreateMediqUserConverter: FallibleConverter<CreateMediqUserDto, CreateMediqUser>,
) {
    private val logger = KotlinLogging.logger {  }
    @MutationMapping("createMediqUser")
    suspend fun createMediqUser(@Argument("createUser") createMediqUserDto: CreateMediqUserDto): MediqUserDto? {
        val createMediqUser = createMediqUserDtoToCreateMediqUserConverter.convert(createMediqUserDto)
            ?: return null.also { logger.debug { "Could not convert $createMediqUserDto to ${CreateMediqUser::class.simpleName}. Returning null" } }
        val mediqUser = createMediqUserService.addUser(createMediqUser)
            ?: return null.also { logger.debug { "Could not add user $createMediqUser. Returning null" } }
        return mediqUserToMediqUserDtoConverter.convert(mediqUser)
    }
}