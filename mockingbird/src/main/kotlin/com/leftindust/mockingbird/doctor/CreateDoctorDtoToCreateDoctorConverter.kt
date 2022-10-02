package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.email.CreateEmailDto
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.ProofOfValidUser
import java.time.LocalDate
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateDoctorDtoToCreateDoctorConverter(
    private val userValidator: FallibleConverter<MediqUserDto.MediqUserUniqueId, ProofOfValidUser>,
    private val createEmailDtoToCreateEmailConverter: FallibleConverter<CreateEmailDto, CreateEmail>,
) : FallibleConverter<CreateDoctorDto, CreateDoctor> {
    private val logger = KotlinLogging.logger { }
    override fun convert(source: CreateDoctorDto): CreateDoctor? {
        return CreateDoctorImpl(
            user = when (source.user.discriminant) {
                CreateDoctorUserDto.CreateDoctorUserDtoType.NoUser -> CreateDoctor.User.NoUser(
                    nameInfo = source.user.nameInfo ?: return null.also { logger.debug { FailedConversionMessage(source) } }
                )

                CreateDoctorUserDto.CreateDoctorUserDtoType.Find -> CreateDoctor.User.Find(
                    userUid = source.user.userUid?.value ?: return null.also { logger.debug { FailedConversionMessage(source) } }
                )

                CreateDoctorUserDto.CreateDoctorUserDtoType.Create -> CreateDoctor.User.Create(
                    uid = source.user.userUid ?: return null.also { logger.debug { FailedConversionMessage(source) } },
                    nameInfo = source.user.nameInfo ?: return null.also { logger.debug { FailedConversionMessage(source) } },
                    group = source.user.group ?: return null.also { logger.debug { FailedConversionMessage(source) } },
                    proofOfValidUser = userValidator.convert(source.user.userUid)
                        ?: return null.also { logger.debug { FailedConversionMessage(source) } }
                )
            },
            phones = source.phones,
            title = source.title,
            clinic = source.clinic,
            dateOfBirth = source.dateOfBirth,
            addresses = source.addresses,
            emails = source.emails.map { createEmailDtoToCreateEmailConverter.convert(it) ?: return null.also { logger.debug { FailedConversionMessage(source) } } },
            patients = source.patients,
        )
    }

    data class CreateDoctorImpl(
        override val user: CreateDoctor.User,
        override val phones: List<CreatePhone>,
        override val title: String?,
        override val clinic: List<ClinicDto.ClinicDtoId>,
        override val dateOfBirth: LocalDate?,
        override val addresses: List<CreateAddress>,
        override val emails: List<CreateEmail>,
        override val patients: List<PatientDto.PatientDtoId>,
    ) : CreateDoctor
}