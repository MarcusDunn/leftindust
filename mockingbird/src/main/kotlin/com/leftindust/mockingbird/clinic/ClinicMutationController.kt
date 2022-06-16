package com.leftindust.mockingbird.clinic


import com.leftindust.mockingbird.InfallibleConverter
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
    suspend fun addClinic(@Argument clinic: CreateClinicDto, dataFetchingEnvironment: DataFetchingEnvironment): ClinicDto {
        val newClinic = createClinicService.addClinic(clinic)
        return clinicToClinicDtoConverter.convert(newClinic)
    }

    @MutationMapping
    @Suppress("UNCHECKED_CAST")
    suspend fun editClinic(@Argument args: Map<String, Any?>): ClinicDto? {
        val clinic = args["clinic"]
        val clinicEdit = MapDelegatingUpdateClinicDto(clinic as Map<String, Any?>)
        val editedClinic = clinicUpdaterService.editClinic(clinicEdit) ?: return null
        return clinicToClinicDtoConverter.convert(editedClinic)
    }
}