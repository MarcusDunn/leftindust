package com.leftindust.mockingbird.user

import org.springframework.stereotype.Controller

@Controller
class UserMutationController {
    suspend fun addUser(user: CreateUserDto): MediqUserDto {
        TODO()
    }
    suspend fun editUser(user: GraphQLUserEditInput): MediqUserDto {
        TODO()
    }
}