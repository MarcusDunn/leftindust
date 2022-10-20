package com.leftindust.mockingbird.patient

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_PATIENT')")
interface CreatePatientService {
    suspend fun addNewPatient(patient: CreatePatient): Patient
}