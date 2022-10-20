package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.email.ReadEmailService
import com.leftindust.mockingbird.person.ReadNameInfoService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class PatientNameInfoQueryController(
    private val readNameInfoService: ReadNameInfoService
) {
    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "firstName")
    suspend fun firstName(patientDto: PatientDto): String {
        return readNameInfoService.getByPatientId(patientDto.id)?.firstName
            ?: throw NullSubQueryException(patientDto, ReadEmailService::getPatientEmails)
    }

    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "middleName")
    suspend fun middleName(patientDto: PatientDto): String {
        return readNameInfoService.getByPatientId(patientDto.id)?.middleName
            ?: throw NullSubQueryException(patientDto, ReadEmailService::getPatientEmails)
    }

    @SchemaMapping(typeName = PatientDto.GRAPHQL_TYPE, field = "lastName")
    suspend fun lastName(patientDto: PatientDto): String {
        return readNameInfoService.getByPatientId(patientDto.id)?.lastName
            ?: throw NullSubQueryException(patientDto, ReadEmailService::getPatientEmails)
    }


}