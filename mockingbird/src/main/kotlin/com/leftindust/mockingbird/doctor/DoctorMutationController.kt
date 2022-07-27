package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.FailedConversionMessage.Companion.FailedConversionMessage
import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.InconvertibleDtoException
import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.phone.CreatePhone
import java.time.LocalDate
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller


@Component
class CreateDoctorDtoToCreateDoctorConverter : FallibleConverter<CreateDoctorDto, CreateDoctor> {
    private val logger = KotlinLogging.logger { }
    override fun convert(source: CreateDoctorDto): CreateDoctor? {
        return CreateDoctorImpl(
            user = when (source.user.discriminant) {
                CreateDoctorUserDto.CreateDoctorUserDtoType.NoUser -> CreateDoctor.User.NoUser(
                    nameInfo = source.user.nameInfo ?: return null.also { logger.debug { FailedConversionMessage(source) } }
                )
                CreateDoctorUserDto.CreateDoctorUserDtoType.Find -> CreateDoctor.User.Find(
                    userUid = source.user.userUid ?: return null.also { logger.debug { FailedConversionMessage(source) } }
                )
                CreateDoctorUserDto.CreateDoctorUserDtoType.Create -> CreateDoctor.User.Create(
                    uid = source.user.uid ?: return null.also { logger.debug { FailedConversionMessage(source) } },
                    nameInfo = source.user.nameInfo ?: return null.also { logger.debug { FailedConversionMessage(source) } },
                    group = source.user.group ?: return null.also { logger.debug { FailedConversionMessage(source) } },
                )
            },
            phones = source.phones,
            title = source.title,
            clinic = source.clinic,
            dateOfBirth = source.dateOfBirth,
            addresses = source.addresses,
            emails = source.emails,
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

@Controller
class DoctorMutationController(
    private val updateDoctorService: UpdateDoctorService,
    private val createDoctorService: CreateDoctorService,
    private val doctorToDoctorDtoInfallibleConverter: InfallibleConverter<Doctor, DoctorDto>,
    private val createDoctorDtoToCreateDoctorConverter: FallibleConverter<CreateDoctorDto, CreateDoctor>,
) {

    @MutationMapping
    suspend fun addDoctor(@Argument("createDoctor") createDoctorDto: CreateDoctorDto): DoctorDto {
        val createDoctor = createDoctorDtoToCreateDoctorConverter.convert(createDoctorDto)
        val newDoctor = createDoctorService.addDoctor(createDoctor ?: throw InconvertibleDtoException<CreateDoctor>(createDoctorDto))
        return doctorToDoctorDtoInfallibleConverter.convert(newDoctor)
    }

    @MutationMapping
    suspend fun editDoctor(doctor: UpdateDoctorDto): DoctorDto? {
        val updatedDoctor = updateDoctorService.editDoctor(doctor)
        return updatedDoctor?.let { doctorToDoctorDtoInfallibleConverter.convert(it) }
    }
}