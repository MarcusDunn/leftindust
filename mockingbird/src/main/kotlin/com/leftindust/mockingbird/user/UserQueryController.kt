package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.input.RangeDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.NameInfoDto
import com.leftindust.mockingbird.person.ReadNameInfoService
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserQueryController(
    private val readUserService: ReadUserService,
    private val mediqUserToMediqUserDtoConverter: InfallibleConverter<MediqUser, MediqUserDto>,
) {
    private val logger = KotlinLogging.logger {  }

    @QueryMapping
    suspend fun userByUserUniqueId(@Argument uniqueId: String): MediqUserDto? {
        val mediqUser = readUserService.getByUserUid(uniqueId)
            ?: return null
        return mediqUserToMediqUserDtoConverter.convert(mediqUser)
    }

    suspend fun usersByRange(range: RangeDto): Flow<MediqUserDto> = TODO()

    suspend fun usersByUserId(uniqueIds: List<String>): Flow<MediqUserDto?> = TODO()

    suspend fun isRegistered(): Boolean = TODO()

    suspend fun firebaseUserInfo(): GraphQLFirebaseInfo = TODO()

    suspend fun doctor(): DoctorDto? = TODO()

    suspend fun patient(): PatientDto? = TODO()

    suspend fun hasPermission(): Boolean = TODO()
}

@Controller
class MediqUserNameInfoQueryController(
    private val readNameInfoService: ReadNameInfoService,
    private val nameInfoToNameInfoDtoConverter: InfallibleConverter<NameInfo, NameInfoDto>,
) {
    @QueryMapping
    suspend fun name(mediqUser: MediqUserDto): NameInfoDto {
        val nameInfo = readNameInfoService.getByUniqueId(mediqUser.id)
            ?: throw NullSubQueryException(mediqUser, ReadNameInfoService::getByUniqueId)
        return nameInfoToNameInfoDtoConverter.convert(nameInfo)
    }
}

@Controller
class MediqUserUserAccountDetailsController(
    private val readUserAccountDetailsService: ReadUserAccountDetailsService,
    private val userAccountDetailsToUserAccountDetailsDtoConverter: InfallibleConverter<UserAccountDetails, UserAccountDetailsDto>,
) {
    @QueryMapping
    fun accountDetails(mediqUser: MediqUserDto): UserAccountDetailsDto {
        val userAccountDetails = readUserAccountDetailsService.getUserInfoByMediqUserUniqueId(mediqUser.id)
            ?: throw NullSubQueryException(mediqUser, ReadUserAccountDetailsService::getUserInfoByMediqUserUniqueId)
        return userAccountDetailsToUserAccountDetailsDtoConverter.convert(userAccountDetails)
    }
}