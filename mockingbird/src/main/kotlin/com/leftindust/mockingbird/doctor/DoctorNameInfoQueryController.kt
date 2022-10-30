package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.NullSubQueryException
import com.leftindust.mockingbird.person.ReadNameInfoService
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorNameInfoQueryController(
    private val readNameInfoService: ReadNameInfoService,
) {
    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "firstName")
    suspend fun firstName(doctorDto: DoctorDto): String {
        return readNameInfoService.getByDoctorId(doctorDto.id)?.firstName
            ?: throw NullSubQueryException(doctorDto, ReadNameInfoService::getByDoctorId)
    }

    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "middleName")
    suspend fun middleName(doctorDto: DoctorDto): String {
        return readNameInfoService.getByDoctorId(doctorDto.id)?.middleName
            ?: throw NullSubQueryException(doctorDto, ReadNameInfoService::getByDoctorId)
    }

    @SchemaMapping(typeName = DoctorDto.GRAPHQL_TYPE, field = "lastName")
    suspend fun lastName(doctorDto: DoctorDto): String {
        return readNameInfoService.getByDoctorId(doctorDto.id)?.lastName
            ?: throw NullSubQueryException(doctorDto, ReadNameInfoService::getByDoctorId)
    }
}