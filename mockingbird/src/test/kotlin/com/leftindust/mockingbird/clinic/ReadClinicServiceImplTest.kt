package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.util.DoctorMother
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ReadClinicServiceImplUnitTest {
    @MockK
    private lateinit var clinicRepository: ClinicRepository

    @MockK
    private lateinit var doctorService: ReadDoctorService

    @Test
    internal fun `check getByDoctorId returns a doctor's clinics when the doctor exists`() = runTest {
        val jennyTheDoctor = DoctorMother.jennyTheDoctorPersisted
        coEvery { doctorService.getByDoctorId(match { it.value == jennyTheDoctor.id }) } returns jennyTheDoctor
        val readClinicServiceImpl = ReadClinicServiceImpl(clinicRepository, doctorService)
        val clinics = readClinicServiceImpl.getByDoctorId(DoctorDto.DoctorDtoId(jennyTheDoctor.id!!))

        assertThat(clinics, equalTo(jennyTheDoctor.clinics.map { it.clinic }))
    }

    @Test
    internal fun `check getByDoctorId returns null when no matching doctor exists`() = runTest {
        val someNonExistentUUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
        coEvery { doctorService.getByDoctorId(match { it.value == someNonExistentUUUid }) } returns null
        val readClinicServiceImpl = ReadClinicServiceImpl(clinicRepository, doctorService)
        val clinics = readClinicServiceImpl.getByDoctorId(DoctorDto.DoctorDtoId(someNonExistentUUUid))

        assertThat(clinics, nullValue())
    }
}