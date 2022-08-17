package com.leftindust.mockingbird.address

import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.PatientMother
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ReadAddressServiceTest {

    @MockK
    private lateinit var addressRepository: AddressRepository

    @MockK
    private lateinit var readDoctorService: ReadDoctorService

    @MockK
    private lateinit var readPatientService: ReadPatientService

    @Test
    internal fun `check getByDoctorId returns a list of addresses when matching Doctor Id exists`() = runTest {
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns DoctorMother.Jenny.entityPersisted
        val readAddressServiceImpl =
            withContext(Dispatchers.IO) {
                ReadAddressServiceImpl(addressRepository, readDoctorService, readPatientService)
            }
        val addresses = readAddressServiceImpl.getByDoctorId(DoctorMother.Jenny.graphqlId)

        MatcherAssert.assertThat(
            addresses,
            Matchers.containsInAnyOrder(DoctorMother.Jenny.addresses.map { Matchers.equalTo(it) })
        )
    }

    @Test
    internal fun `check getByPatientId returns a list of addresses corresponding to a patient's Id`() =
        runTest {
            coEvery { readPatientService.getByPatientId(PatientMother.Dan.graphqlId) } returns PatientMother.Dan.entityPersisted
            val readAddressServiceImpl =
                withContext(Dispatchers.IO) {
                    ReadAddressServiceImpl(addressRepository, readDoctorService, readPatientService)
                }
            val addresses = readAddressServiceImpl.getByPatientId(PatientMother.Dan.graphqlId)

            MatcherAssert.assertThat(
                addresses,
                Matchers.containsInAnyOrder(PatientMother.Dan.addresses.map { Matchers.equalTo(it) })
            )
        }

}