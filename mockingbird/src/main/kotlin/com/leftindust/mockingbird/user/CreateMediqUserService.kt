package com.leftindust.mockingbird.user

interface CreateMediqUserService {
    suspend fun addUser(user: CreateMediqUser): MediqUser?
}

