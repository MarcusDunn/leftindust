package com.leftindust.mockingbird.user

import com.google.firebase.auth.FirebaseAuth
import dev.forkhandles.result4k.onFailure
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class MediqUserMutationController(
    private val createMediqUserService: CreateMediqUserService,
    @Autowired val firebaseAuth: FirebaseAuth
) {
    private val logger = KotlinLogging.logger { }

    @MutationMapping("createMediqUser")
    suspend fun createMediqUser(@Argument("createUser") createMediqUserDto: CreateMediqUserDto): MediqUserDto? {
        val createMediqUser = createMediqUserDto.toCreateMediqUser().onFailure { throw it.reason.toMockingbirdException() }
        val mediqUser = createMediqUserService.addUser(createMediqUser)
            ?: return null.also { logger.debug { "Could not add user $createMediqUser. Returning null" } }
        return mediqUser.toMediqUserDto().onFailure { throw it.reason.toMockingbirdException() }
    }
}
