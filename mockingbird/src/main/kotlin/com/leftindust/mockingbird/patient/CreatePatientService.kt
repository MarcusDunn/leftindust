package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.MockingbirdException
import dev.forkhandles.result4k.Result4k
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_PATIENT')")
interface CreatePatientService {
    suspend fun addNewPatient(patient: CreatePatient): Result4k<Patient, MockingbirdException>
}