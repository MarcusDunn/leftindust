package com.leftindust.mockingbird.clinic


import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClinicEntityToClinicConverter : InfallibleConverter<ClinicEntity, Clinic> {
    override fun convert(source: ClinicEntity): Clinic {
        return ClinicImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            name = source.name,
        )
    }

    private data class ClinicImpl(
        override val id: UUID,
        override val name: String,
    ) : Clinic
}