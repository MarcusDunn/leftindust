package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.InfallibleConverter
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.user.MediqUserRepository
import com.leftindust.mockingbird.user.MediqUserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ReadNameInfoServiceImpl(
    @Autowired private val mediqUserRepository: MediqUserRepository,
    val patientRepository: PatientRepository,
    val nameInfoEntityToNameInfoConverter: InfallibleConverter<NameInfoEntity, NameInfo>,

    ) : ReadNameInfoService {
    override fun getByUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): NameInfo? {
        TODO("Not yet implemented")
    }
    override fun getByPatientId(patientDtoId: PatientDto.PatientDtoId): NameInfo? {
        val patientEntity = patientRepository.findByIdOrNull(patientDtoId.value)
            ?: return null
        return nameInfoEntityToNameInfoConverter.convert(patientEntity.nameInfoEntity);
    }
}