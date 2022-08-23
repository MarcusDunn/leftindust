package com.leftindust.mockingbird.clinic


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component

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