package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.clinic.*
import com.leftindust.mockingbird.email.CreateEmailService
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.ignore
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.CreateNameInfoService
import com.leftindust.mockingbird.phone.CreatePhoneService
import com.leftindust.mockingbird.user.CreateMediqUser
import com.leftindust.mockingbird.user.CreateMediqUserService
import com.leftindust.mockingbird.user.MediqGroupDto
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.user.ProofOfValidUser
import com.leftindust.mockingbird.user.ReadMediqUserService
import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class CreateDoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val createMediqUserService: CreateMediqUserService,
    private val readMediqUserService: ReadMediqUserService,
    private val createAddressService: CreateAddressService,
    private val createEmailService: CreateEmailService,
    private val createNameInfoService: CreateNameInfoService,
    private val createPhoneService: CreatePhoneService,
    private val readClinicService: ReadClinicService,
    private val readPatientService: ReadPatientService,
    private val updateClinicService: UpdateClinicService
) : CreateDoctorService {

    override suspend fun addDoctor(createDoctor: CreateDoctor): Doctor {
        val (user, nameInfo) = when (val user = createDoctor.user) {
            is CreateDoctor.User.Create -> createMediqUserService.addUser(
                CreateMediqUserImpl(
                    uid = user.uid,
                    nameInfo = user.nameInfo,
                    group = user.group,
                    doctor = null,
                    proofOfValidUser = user.proofOfValidUser
                )
            ) to null
            is CreateDoctor.User.Find -> readMediqUserService.getByUserUid(user.userUid) to null
            is CreateDoctor.User.NoUser -> null to createNameInfoService.createNameInfo(user.nameInfo)
        }
        val notNullNameInfo = nameInfo ?: user?.nameInfo!!
        val doctor = Doctor(
            nameInfo = notNullNameInfo,
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
                    readPatientService.getByPatientId(it)
                        ?: throw IllegalArgumentException("No such patient with id $it")
                }.forEach { addPatient(it) }

            updateClinics(createDoctor.clinic, this)
        }
        return doctorRepository.save(doctor)
    }

    private suspend fun updateClinics(
        clinicsEdit: List<ClinicDto.ClinicDtoId>,
        doctor: Doctor
    ) {
        val newDoctorId = DoctorDto.DoctorDtoId(doctor.id ?: return)

        val clinicIdToClinicDoctors = clinicsEdit
            .map {
                val clinic = readClinicService.getByClinicId(it)
                    ?: throw IllegalArgumentException("No such clinic with id $it")
                clinic.id to clinic.doctors.toMutableSet()
            }
        clinicIdToClinicDoctors.forEach { clinicIdToDoctors ->
            val editClinic = ClinicEditDto(
                ClinicDto.ClinicDtoId(clinicIdToDoctors.first),
                ignore(),
                ignore(),
                Updatable.Update(listOf(newDoctorId, *clinicIdToDoctors.second.toTypedArray()))
            )
            updateClinicService.editClinic(editClinic)
        }

        TODO("Add the clinic to the doctor. Clinic cannot currently be converted to ClinicEntity for ClinicDoctorEntity")
    }

    data class CreateMediqUserImpl(
        override val uid: MediqUserDto.MediqUserUniqueId,
        override val nameInfo: CreateNameInfo,
        override val group: MediqGroupDto.MediqGroupId,
        override val doctor: DoctorDto.DoctorDtoId?,
        override val proofOfValidUser: ProofOfValidUser,
    ) : CreateMediqUser
}