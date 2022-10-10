package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
@DataJpaTest
internal class ReadAddressServiceTest {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockK
    private lateinit var doctorRepository: DoctorRepository

    @MockK
    private lateinit var patientRepository: PatientRepository

    @Test
    internal fun `check getByDoctorId returns a list of addresses when matching Doctor Id exists`() = runTest {
        coEvery { doctorRepository.findByIdOrNull(DoctorMother.Jenny.id) } returns DoctorMother.Jenny.entityTransient
        val readAddressServiceImpl = ReadAddressServiceImpl(patientRepository, doctorRepository)

        val addresses = readAddressServiceImpl.getByDoctorId(DoctorMother.Jenny.graphqlId)

        MatcherAssert.assertThat(
            addresses,
            Matchers.containsInAnyOrder(DoctorMother.Jenny.addresses.map { Matchers.equalTo(it) })
        )
    }

    @Test
    internal fun `check getByPatientId returns a list of addresses corresponding to a patient's Id`() = runTest {
        coEvery { patientRepository.findByIdOrNull(PatientMother.Dan.id) } returns PatientMother.Dan.entityDetached
        val readAddressServiceImpl = ReadAddressServiceImpl(patientRepository, doctorRepository)
        val addresses = readAddressServiceImpl.getByPatientId(PatientMother.Dan.graphqlId)

        MatcherAssert.assertThat(
            addresses,
            Matchers.containsInAnyOrder(PatientMother.Dan.addressesDetached.map { Matchers.equalTo(it) })
        )
    }
}