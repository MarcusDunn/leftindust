package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.InfallibleConverter
import org.springframework.stereotype.Component

@Component
class ClinicToClinicEntityConverter: InfallibleConverter<Clinic,ClinicEntity> {
    override fun convert(source: Clinic): ClinicEntity {
        return ClinicEntity(
            name = source.name,
            address = source.address,
            doctors = source.doctors
        )
    }
}