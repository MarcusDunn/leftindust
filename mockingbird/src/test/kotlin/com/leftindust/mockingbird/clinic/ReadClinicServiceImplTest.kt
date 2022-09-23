package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.util.ClinicMother.DansClinic
import com.leftindust.mockingbird.util.DoctorMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ReadClinicServiceImplUnitTest {
    @MockK
    private lateinit var clinicRepository: ClinicRepository

    @MockK
    private lateinit var doctorRepository: DoctorRepository

    private val clinicEntityToClinicConverter = ClinicEntityToClinicConverter()

    @Test
    internal fun `check getByDoctorId returns a doctor's clinics when the doctor exists`() = runTest {
        coEvery { doctorRepository.findByIdOrNull(DoctorMother.Jenny.id) } returns DoctorMother.Jenny.entityPersisted
        val readClinicService = ReadClinicServiceImpl(clinicEntityToClinicConverter, clinicRepository, doctorRepository)
        val clinics = readClinicService.getByDoctorId(DoctorMother.Jenny.graphqlId)

        coVerify(exactly = 1) { doctorRepository.findByIdOrNull(DoctorMother.Jenny.graphqlId.value) }
        assertThat(
            clinics,
            containsInAnyOrder(DoctorMother.Jenny.clinics.map { equalTo(clinicEntityToClinicConverter.convert(it)) })
        )
    }

    @Test
    internal fun `check getByDoctorId returns null when no matching doctor exists`() = runTest {
        val someNonExistentUUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
        coEvery { doctorRepository.findByIdOrNull(match { it == someNonExistentUUUid }) } returns null
        val readClinicService = ReadClinicServiceImpl(clinicEntityToClinicConverter, clinicRepository, doctorRepository)
        val clinics = readClinicService.getByDoctorId(DoctorDto.DoctorDtoId(someNonExistentUUUid))

        coVerify(exactly = 1) { doctorRepository.findByIdOrNull(match { it == someNonExistentUUUid }) }
        assertThat(clinics, nullValue())
    }
}

// One could argue that we should be testing ClinicRepository directly, but it is created dynamically by spring we'll consister it an implementation detail
// of the ReadClinicService. This is up to taste.
// If we had created methods via https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods
// we would likely test those in isolation from the services that use them.
@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
internal class ReadClinicServiceImplDataTest(
    @Autowired private val testEntityManager: TestEntityManager,
    @Autowired private val clinicRepository: ClinicRepository
) {
    // create a mock SecurityWebFilterChain in order to not have to deal with security (we can test that separately)
    @MockkBean
    private lateinit var securityWebFilterChain: SecurityWebFilterChain

    @MockkBean
    private lateinit var doctorRepository: DoctorRepository

    private val clinicEntityToClinicConverter = ClinicEntityToClinicConverter()

    @Test
    internal fun `check returns a clinic when the database has a matching clinic`() = runTest {
        // set up the test by adding a clinic to the database
        val dansClinicEntity = testEntityManager.persist(DansClinic.entityUnpersisted)
        val dansClinic = clinicEntityToClinicConverter.convert(dansClinicEntity)

        // create the service under test using the *real* ClinicRepository and a fake ReadDoctorService
        val readClinicService = ReadClinicServiceImpl(clinicEntityToClinicConverter, clinicRepository, doctorRepository)

        // run the query using the returned Clinic's id (assigned by the database)
        val returnedClinic = readClinicService.getByClinicId(ClinicDto.ClinicDtoId(dansClinic.id))

        // assert that the returned entity is the same one we just persisted.
        assertThat(returnedClinic, equalTo(dansClinic))
    }

    @Test
    @Disabled
    internal fun `check returns null when the database has no matching clinic`() = runTest {
        // create a fake UUID we know does not exist in the database
        val someNonExistentUuid = UUID.fromString("d25292ba-ba8e-4098-8295-806712f70bd1")

        // create the service under test using the *real* ClinicRepository and a fake ReadDoctorService
        val readClinicService = ReadClinicServiceImpl(clinicEntityToClinicConverter, clinicRepository, doctorRepository)

        // run the query using the fake UUID
        val returnedClinic = readClinicService.getByClinicId(ClinicDto.ClinicDtoId(someNonExistentUuid))

        // assert that there is no clinic returned
        assertThat(returnedClinic, nullValue())
        TODO("Fix this test so that calling clinicRepository.findByIdOrNull does not throw (called in getClinicById) when the record is not found")
    }
}