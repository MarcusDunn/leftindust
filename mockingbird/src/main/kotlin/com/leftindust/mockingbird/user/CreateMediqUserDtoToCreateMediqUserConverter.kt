package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.FallibleConverter
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.person.CreateNameInfo
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CreateMediqUserDtoToCreateMediqUserConverter(private val userValidator: FallibleConverter<MediqUserDto.MediqUserUniqueId, ProofOfValidUser>) :
    FallibleConverter<CreateMediqUserDto, CreateMediqUser> {
    private val logger = KotlinLogging.logger { }

    override fun convert(source: CreateMediqUserDto): CreateMediqUser? {
        return CreateMediqUserImpl(
            uid = source.uid,
            nameInfo = source.nameInfo,
            group = source.group,
            doctor = source.doctor,
            proofOfValidUser = userValidator.convert(source.uid) ?: return null.also { logger.debug { "Failed to validate $source" } },
        )
    }

    private data class CreateMediqUserImpl(
        override val uid: MediqUserDto.MediqUserUniqueId,
        override val nameInfo: CreateNameInfo,
        override val group: MediqGroupDto.MediqGroupId?,
        override val doctor: DoctorDto.DoctorDtoId?,
        override val proofOfValidUser: ProofOfValidUser,
    ) : CreateMediqUser

}