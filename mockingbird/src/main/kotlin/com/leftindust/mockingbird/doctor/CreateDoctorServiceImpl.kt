package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.ClinicDto
import com.leftindust.mockingbird.clinic.ClinicRepository
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.*
import dev.forkhandles.result4k.onFailure
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val clinicRepository: ClinicRepository,
    private val patientRepository: PatientRepository,
    private val createMediqUserService: CreateMediqUserService,
    private val readMediqUserService: ReadMediqUserService,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val createNameInfoService: CreateNameInfoService,
    private val createPhoneService: CreatePhoneService,
) : CreateDoctorService {

    override suspend fun addDoctor(createDoctor: CreateDoctor): Doctor {
        val (user, nameInfo) = when (val user = createDoctor.user) {
            is CreateDoctor.User.Create -> createMediqUserService.addUser(
                CreateMediqUserImpl(
                    uid = user.uid,
                    nameInfo = user.nameInfo,
                    group = user.group,
                    doctor = null,
                )
            ) to null

            is CreateDoctor.User.Find -> readMediqUserService.getByUserUid(user.userUid) to null
            is CreateDoctor.User.NoUser -> null to createNameInfoService.createNameInfo(user.nameInfo)
        }
        val notNullNameInfo = nameInfo ?: user?.nameInfoEntity!!
        val doctor = DoctorEntity(
            nameInfoEntity = notNullNameInfo,
            addresses = createDoctor.addresses.map { createAddressService.createAddress(it) }.toMutableSet(),
            emails = createDoctor.emails.map { createEmailService.createEmail(it) }.toMutableSet(),
            phones = createDoctor.phones.map { createPhoneService.createPhone(it) }.toMutableSet(),
            user = user,
            events = mutableSetOf(), // not currently possible to set on create doctor.
            thumbnail = null,
            title = createDoctor.title,
            dateOfBirth = createDoctor.dateOfBirth,
            clinics = mutableSetOf(), // set in apply block
            patients = mutableSetOf(), // set in apply block
        ).apply {
            createDoctor.patients
                .map {
                    patientRepository.findByIdOrNull(it.value)
                        ?: throw IllegalArgumentException("No such patient with id $it")
                }.forEach { addPatient(it) }

//            updateClinics(createDoctor.clinic, this)
        }
        return (doctorRepository.save(doctor)).toDoctor().onFailure { throw it.reason.toMockingbirdException() }
    }

    private suspend fun updateClinics(
        clinicsEdit: List<ClinicDto.ClinicDtoId>,
        doctor: DoctorEntity
    ) {
        // this always fails
        doctor.id ?: throw IllegalArgumentException("Clinic is missing a doctor id")

        clinicsEdit.map {
            val clinicEntity = clinicRepository.findById(it.value).orElseThrow {
                IllegalArgumentException("No such clinic with id ${it.value}")
            }
            clinicEntity.addDoctor(doctor)
            clinicRepository.save(clinicEntity)
        }
    }

    data class CreateMediqUserImpl(
        override val uid: MediqUserDto.MediqUserUniqueId,
        override val nameInfo: CreateNameInfo,
        override val group: MediqGroupDto.MediqGroupId,
        override val doctor: DoctorDto.DoctorDtoId?,
    ) : CreateMediqUser
}
