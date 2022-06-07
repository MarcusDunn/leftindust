package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_CLINIC')")
interface ClinicCreationService {
    sealed class ClinicCreationResult {
        data class Success(val clinic: Clinic) : ClinicCreationResult()
        sealed class Failure(val message: String) : ClinicCreationResult() {
            data class DoctorIdsDoNotExist(val missingDoctors: List<DoctorDto.DoctorDtoId>) : Failure("The doctors with id's $missingDoctors do not exist")
        }
    }

    suspend fun addClinic(createClinic: CreateClinic): ClinicCreationResult
}