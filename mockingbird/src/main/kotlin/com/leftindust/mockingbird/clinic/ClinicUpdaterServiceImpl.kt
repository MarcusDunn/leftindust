package com.leftindust.mockingbird.clinic


import com.leftindust.mockingbird.*
import com.leftindust.mockingbird.address.CreateAddressService
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.applyUpdatable
import com.leftindust.mockingbird.patient.UpdateAddress
import com.leftindust.mockingbird.patient.UpdateAddressDto
import com.leftindust.mockingbird.person.UpdateAddressService
import javax.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
@Transactional
class ClinicUpdaterServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val doctorRepository: DoctorRepository,
    private val updateAddressService: UpdateAddressService,
    private val clinicEntityToClinicConverter: InfallibleConverter<ClinicEntity, Clinic>,
) : UpdateClinicService {
    private val logger = KotlinLogging.logger { }

    override suspend fun editClinic(clinicEdit: ClinicEdit): Clinic? {
        val clinicId = clinicEdit.cid.value
        val existingClinic = clinicRepository.findByIdOrNull(clinicId)

        return if (existingClinic == null) {
            logger.warn { NoUpdatesOccurredNoEntityWithId(ClinicEntity::class, clinicId) }
            null
        }
        else {
            updateAddress(clinicEdit.address, existingClinic)
            updateDoctors(clinicEdit.doctors, existingClinic)
            clinicEdit.name.applyUpdatable(existingClinic, existingClinic::name, logger)
            clinicRepository.save(existingClinic)
            return clinicEntityToClinicConverter.convert(existingClinic)
        }
    }

    private suspend fun updateDoctors(
        doctorsEdit: Updatable<List<DoctorDto.DoctorDtoId>>,
        clinic: ClinicEntity,
    ) {
        when (doctorsEdit) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(clinic, clinic::doctors) }
            }
            is Updatable.Update -> {
                val doctorIds = doctorsEdit.value
                val newDoctorsToIds = doctorIds.map { it to doctorRepository.findByIdOrNull(it.value) }
                newDoctorsToIds.forEach {
                    if (it.second == null)
                        logger.warn { NoOpUpdatedEntityFieldMessage(clinic, clinic::doctors, "Could not find a doctor with id: ${it.first}") }
                }
                val newDoctors = newDoctorsToIds.mapNotNull { it.second }

                clinic.clearDoctors()

                newDoctors.forEach {
                    clinic.addDoctor(it)
                    logger.trace { AddedElementMessage(clinic, clinic::doctors, it) }
                }
            }
        }
    }

    private suspend fun updateAddress(
        addressEdit: Updatable<UpdateAddress>,
        clinic: ClinicEntity,
    ) {
        when (addressEdit) {
            is Updatable.Ignore -> {
                logger.trace { NoOpUpdatedEntityFieldMessage(clinic, clinic::address) }
            }
            is Updatable.Update -> {
                updateAddressService.updateAddress(addressEdit.value, clinic.address)
                // val createAddress = updateAddressService.updateAddress(addressEdit.value, clinic.address)
//                clinic.address = createAddress
//                logger.trace { SetEntityFieldMessage(clinic, clinic::address, createAddress) }
            }
        }
    }
}