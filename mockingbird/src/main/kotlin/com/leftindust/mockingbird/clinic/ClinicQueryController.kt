package com.leftindust.mockingbird.clinic

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class ClinicQueryController(
    private val readClinicService: ReadClinicService,
) {

    @QueryMapping
    suspend fun clinicByClinicId(@Argument clinicId: ClinicDto.ClinicDtoId): ClinicDto? {
        val clinic = readClinicService.getByClinicId(clinicId)
        return clinic?.let { it.toClinicDto() }
    }
}