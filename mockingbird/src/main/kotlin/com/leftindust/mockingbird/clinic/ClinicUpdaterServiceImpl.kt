package com.leftindust.mockingbird.clinic

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
            logger.warn { "No clinic was updated via $clinicEdit. No clinic with $clinicId was found" }
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
        when (nameEdit) {
            is Updatable.Ignore -> {
                logger.trace { "Did not update $clinic name. Update was $nameEdit" }
            }
            is Updatable.Update -> {
                clinic.name = nameEdit.value
                logger.trace { "Updated $clinic name to ${nameEdit.value}" }
            }
        }
    }

    private suspend fun updateDoctors(
        doctorsEdit: Updatable<List<DoctorDto.DoctorDtoId>>,
        clinic: Clinic,
    ) {
        when (doctorsEdit) {
            is Updatable.Ignore -> {
                logger.trace { "Did not update $clinic doctors. Update was Ignore" }
            }
            is Updatable.Update -> {
                val doctorIds = doctorsEdit.value
                val newDoctors = doctorIds.map { it to readDoctorService.getByDoctorId(it) }

                clinic.clearDoctors()

                newDoctors.forEach { (id, doctor) ->
                    if (doctor == null) {
                        logger.warn { "Did not add a doctor with id $id to $clinic. Could not find a doctor with that id" }
                    } else {
                        clinic.addDoctor(doctor)
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
                logger.trace { "Did not update $clinic address. Update was Ignore" }
            }
            is Updatable.Update -> {
                val createAddress = createAddressService.createAddress(addressEdit.value)
                logger.trace { "Updated $clinic address to $createAddress. Update was $addressEdit" }
                clinic.address = createAddress
            }
        }
    }
}