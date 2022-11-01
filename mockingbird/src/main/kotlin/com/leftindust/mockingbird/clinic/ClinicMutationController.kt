package com.leftindust.mockingbird.clinic


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.graphql.types.input.toMap
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class ClinicMutationController(
    private val createClinicService: CreateClinicService,
    private val clinicUpdaterService: UpdateClinicService,
    private val clinicToClinicDtoConverter: InfallibleConverter<Clinic, ClinicDto>,
) {

    @MutationMapping
    suspend fun addClinic(@Argument clinic: CreateClinicGraphQlDto, dataFetchingEnvironment: DataFetchingEnvironment): ClinicDto {
        val newClinic = createClinicService.addClinic(clinic.toCreateClinic())
        return clinicToClinicDtoConverter.convert(newClinic)
    }

    @MutationMapping
    suspend fun editClinic(@Argument clinic: UpdateClinicGraphQlDtoDto): ClinicDto? {
        @Suppress("UNCHECKED_CAST")
        val clinicEdit = MapDelegatingUpdateClinicDto(toMap(clinic))
        val editedClinic = clinicUpdaterService.editClinic(clinicEdit.toClinicEdit()) ?: return null
        return clinicToClinicDtoConverter.convert(editedClinic)
    }
}