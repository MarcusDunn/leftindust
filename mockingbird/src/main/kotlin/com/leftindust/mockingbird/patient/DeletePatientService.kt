package com.leftindust.mockingbird.patient

import org.springframework.security.access.prepost.PreAuthorize


@PreAuthorize("hasAuthority('DELETE_PATIENT')")
interface DeletePatientService {
    suspend fun removeByPatientId(pid: PatientDto.PatientDtoId): Patient
}