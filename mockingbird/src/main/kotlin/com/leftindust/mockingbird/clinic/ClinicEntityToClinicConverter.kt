package com.leftindust.mockingbird.clinic


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.address.Address
import com.leftindust.mockingbird.doctor.ClinicDoctorEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClinicEntityToClinicConverter : InfallibleConverter<ClinicEntity, Clinic> {
    override fun convert(source: ClinicEntity): Clinic {
        return Clinic(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            name = source.name,
            address = source.address,
            doctors = source.doctors
        )
    }
}