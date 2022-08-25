package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullSubQueryException
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

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