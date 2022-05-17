package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.LogMessage
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import org.springframework.transaction.annotation.Transactional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
@Transactional
class ReadClinicServiceImpl(
    private val clinicRepository: ClinicRepository,
    private val doctorService: ReadDoctorService,
) : ReadClinicService {
    private val logger = LoggerFactory.getLogger(ReadClinicServiceImpl::class.java)

    override suspend fun getByDoctor(did: DoctorDto.DoctorDtoId): Flow<Clinic>? {
        val doctor = doctorService.getByDoctorId(did) ?: run {
            logger.trace(LogMessage("Returning a null ${Clinic::class.simpleName} from ${ReadClinicService::getByDoctor.name}", "No ${Doctor::class.simpleName} with id $did was found").toString())
            return null
        }
        return doctor.clinics.map { it.clinic }.asFlow()
    }

    override suspend fun getByClinicId(cid: ClinicDto.ClinicDtoId): Clinic? {
        return clinicRepository.findById(cid.value).orElse(null) ?: run {
            logger.trace(LogMessage("Returning a null ${Clinic::class.simpleName} from ${ReadClinicService::getByClinicId.name}", "No ${Clinic::class.simpleName} with id $cid was found").toString())
            null
        }
    }
}