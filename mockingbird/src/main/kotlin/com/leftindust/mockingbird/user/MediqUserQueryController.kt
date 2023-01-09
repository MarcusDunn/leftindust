package com.leftindust.mockingbird.user

import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserQueryController(
    private val readMediqUserService: ReadMediqUserService,
) {
    @QueryMapping("userByUserUniqueId")
    suspend fun userByUserUniqueId(@Argument("uniqueId") uniqueId: String): MediqUserDto? {
        val mediqUser = readMediqUserService.getByUserUid(uniqueId)
            ?: return null
        return mediqUser.toMediqUserDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}

