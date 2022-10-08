package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.NullEntityIdInConverterException
import com.leftindust.mockingbird.person.NameInfo
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.UUID

@Component
class DoctorEntityToDoctorConverter : InfallibleConverter<DoctorEntity, Doctor> {
    override fun convert(source: DoctorEntity): Doctor {
        return DoctorImpl(
            id = source.id ?: throw NullEntityIdInConverterException(source),
            nameInfo = source.nameInfoEntity,
            thumbnail = source.thumbnail,
            title = source.title,
            dateOfBirth = source.dateOfBirth,
        )
    }

    private data class DoctorImpl(
        override val id: UUID,
        override val nameInfo: NameInfo,
        override val thumbnail: ByteArray?,
        override val title: String?,
        override val dateOfBirth: LocalDate?
    ) : Doctor
}