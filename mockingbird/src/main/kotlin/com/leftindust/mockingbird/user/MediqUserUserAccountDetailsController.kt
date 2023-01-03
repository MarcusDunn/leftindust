package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.NullSubQueryException
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserUserAccountDetailsController(
    private val readUserAccountDetailsService: ReadUserAccountDetailsService,
) {
    @QueryMapping
    fun accountDetails(mediqUser: MediqUserDto): UserAccountDetailsDto {
        val userAccountDetails = readUserAccountDetailsService.getUserInfoByMediqUserUniqueId(mediqUser.id)
            ?: throw NullSubQueryException(mediqUser, ReadUserAccountDetailsService::getUserInfoByMediqUserUniqueId)
        return userAccountDetails.toUserAccountDetailsDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}
