package com.leftindust.mockingbird.clinic


import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class ClinicMutationController(
    private val createClinicService: CreateClinicService,
    private val clinicUpdaterService: UpdateClinicService,
) {

    @MutationMapping
    suspend fun addClinic(@Argument clinic: CreateClinicGraphQlDto, dataFetchingEnvironment: DataFetchingEnvironment): ClinicDto {
        val newClinic = createClinicService.addClinic(clinic.toCreateClinic())
        return newClinic.toClinicDto()
    }

    @MutationMapping("editClinic")
    suspend fun editClinic(@Argument("clinic") clinicEdit: ArgumentValueUpdateClinicDto): ClinicDto? {
        val toClinicEditDto = clinicEdit.toClinicEditDto()
        val editedClinic = clinicUpdaterService.editClinic(toClinicEditDto.toClinicEdit()) ?: return null
        return editedClinic.toClinicDto()
    }
}