package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.graphql.types.input.RangeDto
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Controller

@Controller
class UserQueryController(
    private val firebaseFetcher: UserFetcher,
) {
    suspend fun user(uid: String): MediqUserDto {
        TODO()
    }

    suspend fun usersByRange(range: RangeDto): Flow<MediqUserDto> = TODO()

    suspend fun usersByUserId(uniqueIds: List<String>): Flow<MediqUserDto?> = TODO()
}