package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.AddedElementMessage
import com.leftindust.mockingbird.NoOpUpdatedEntityFieldMessage
import com.leftindust.mockingbird.NoUpdatesOccurredNoEntityWithId
import com.leftindust.mockingbird.SetEntityFieldMessage
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.graphql.types.Updatable
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class ClinicUpdaterServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val createAddressService: CreateAddressService,
    private val readDoctorService: ReadDoctorService,
) : UpdateClinicService {
    private val logger = KotlinLogging.logger { }

    override suspend fun editClinic(clinicEdit: ClinicEdit): Clinic? {
        val clinicId = clinicEdit.cid.value
        val clinic = clinicRepository.findById(clinicId).orElse(null)

        return if (clinic == null) {
            logger.warn { NoUpdatesOccurredNoEntityWithId(Clinic::class, clinicId) }
            null
        } else {
            updateAddress(clinicEdit.address, clinic)
            updateDoctors(clinicEdit.doctors, clinic)
            updateName(clinicEdit.name, clinic)

            clinicRepository.save(clinic)
        }
    }

    private fun updateName(
        nameEdit: Updatable<String>,
        clinic: Clinic,
    ) {
        return when (nameEdit) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(clinic, clinic::name) }
            }
            is Updatable.Update -> {
                clinic.name = nameEdit.value
                logger.trace { SetEntityFieldMessage(clinic, clinic::name, nameEdit.value) }
            }
        }
    }

    private suspend fun updateDoctors(
        doctorsEdit: Updatable<List<DoctorDto.DoctorDtoId>>,
        clinic: Clinic,
    ) {
        when (doctorsEdit) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(clinic, clinic::doctors) }
            }
            is Updatable.Update -> {
                val doctorIds = doctorsEdit.value
                val newDoctors = doctorIds.map { it to readDoctorService.getByDoctorId(it) }

                clinic.clearDoctors()

                newDoctors.forEach { (id, doctor) ->
                    if (doctor == null) {
                        logger.warn { NoOpUpdatedEntityFieldMessage(clinic, clinic::doctors, "Could not find a doctor with id: $id") }
                    } else {
                        val clinicDoctorEntity = clinic.addDoctor(doctor)
                        logger.trace { AddedElementMessage(clinic, clinic::doctors, clinicDoctorEntity) }
                    }

                }
            }
        }
    }

    private suspend fun updateAddress(
        addressEdit: Updatable<CreateAddressDto>,
        clinic: Clinic,
    ) {
        when (addressEdit) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(clinic, clinic::address) }
            }
            is Updatable.Update -> {
                val createAddress = createAddressService.createAddress(addressEdit.value)
                clinic.address = createAddress
                logger.trace { SetEntityFieldMessage(clinic, clinic::address, createAddress) }
            }
        }
    }
}