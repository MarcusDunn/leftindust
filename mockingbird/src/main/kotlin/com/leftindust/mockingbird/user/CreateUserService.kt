package com.leftindust.mockingbird.user

interface CreateUserService {
    fun addUser(user: CreateUserDto): MediqUser
}
