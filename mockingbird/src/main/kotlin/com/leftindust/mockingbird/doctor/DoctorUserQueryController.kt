package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.ReadMediqUserService
import com.leftindust.mockingbird.user.toMediqUserDto
import dev.forkhandles.result4k.onFailure
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorUserQueryController(
    private val userService: ReadMediqUserService,
) {
    @SchemaMapping
    suspend fun user(doctorDto: DoctorDto): MediqUserDto? {
        return userService.findByDoctorId(doctorDto.id)?.let { it.toMediqUserDto().onFailure { throw it.reason.toMockingbirdException() } }
    }
}
