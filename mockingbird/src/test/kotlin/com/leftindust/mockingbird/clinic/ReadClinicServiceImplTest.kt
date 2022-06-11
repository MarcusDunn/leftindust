package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.util.ClinicMother
import com.leftindust.mockingbird.util.DoctorMother
import com.ninjasquad.springmockk.MockkBean
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.security.web.server.SecurityWebFilterChain

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

@DataJpaTest
internal class ReadClinicServiceImplDataTest(
    @Autowired private val testEntityManager: TestEntityManager,
    @Autowired private val clinicRepository: ClinicRepository,
) {
    @MockkBean
    private lateinit var securityWebFilterChain: SecurityWebFilterChain

    @MockkBean
    private lateinit var doctorService: ReadDoctorService

    @Test
    internal fun `check returns a clinic when the database has a matching clinic`() = runTest {
        val dansClinic = testEntityManager.persist(ClinicMother.dansClinicWithoutId)

        val readClinicService = ReadClinicServiceImpl(clinicRepository, doctorService)

        val returnedClinic = readClinicService.getByClinicId(ClinicDto.ClinicDtoId(dansClinic.id!!))

        assertThat(returnedClinic, equalTo(dansClinic))
    }

    @Test
    internal fun `check returns null when the database has no matching clinic`() = runTest {
        val someNonExistentUUUid = UUID.fromString("d25292ba-ba8e-4098-8295-806712f70bd1")

        val readClinicService = ReadClinicServiceImpl(clinicRepository, doctorService)

        val returnedClinic = readClinicService.getByClinicId(ClinicDto.ClinicDtoId(someNonExistentUUUid))

        assertThat(returnedClinic, nullValue())
    }
}