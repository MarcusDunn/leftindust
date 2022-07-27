package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.contact.ReadContactService
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.doctor.ReadDoctorService
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.util.ContactMother.Aydan
import com.leftindust.mockingbird.util.DoctorMother.Jenny
import com.leftindust.mockingbird.util.EmailMother
import com.leftindust.mockingbird.util.PatientMother.Dan
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class ReadEmailServiceImplUnitTest {
    @MockK
    private lateinit var emailRepository: EmailRepository

    @MockK
    private lateinit var readDoctorService: ReadDoctorService

    @MockK
    private lateinit var readPatientService: ReadPatientService

    @MockK
    private lateinit var readContactService: ReadContactService

    @Test
    internal fun `check getByDoctorId returns a list of email addresses when matching Doctor Id exists`() = runTest {
        coEvery { readDoctorService.getByDoctorId(Jenny.graphqlId) } returns Jenny.entityPersisted
        val readEmailServiceImpl =
            ReadEmailServiceImpl(emailRepository, readDoctorService, readPatientService, readContactService)
        val emails = readEmailServiceImpl.getByDoctorId(Jenny.graphqlId)

        assertThat(emails, containsInAnyOrder(Jenny.emails.map { equalTo(it) }))
    }

    @Test
    internal fun `check getByDoctorId returns null when no matching Doctor Id exist corresponding to any email address`() =
        runTest {
            val someNonExistentUUid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
            coEvery { readDoctorService.getByDoctorId(match { it.value == someNonExistentUUid }) } returns null
            val readEmailServiceImpl =
                ReadEmailServiceImpl(emailRepository, readDoctorService, readPatientService, readContactService)
            val emails = readEmailServiceImpl.getByDoctorId(DoctorDto.DoctorDtoId(someNonExistentUUid))

            assertThat(emails, nullValue())
        }

    @Test
    internal fun `check getPatientEmails returns a list of email addresses corresponding to a patient's Id`() =
        runTest {
            coEvery { readPatientService.getByPatientId(Dan.graphqlid) } returns Dan.entityPersisted
            val readEmailServiceImpl = ReadEmailServiceImpl(emailRepository, readDoctorService, readPatientService, readContactService)
            val emails = readEmailServiceImpl.getPatientEmails(Dan.graphqlid)

            assertThat(emails, containsInAnyOrder(Dan.emails.map { equalTo(it) }))
        }

    @Test
    internal fun `check getContactEmails returns a list of emails addresses corresponding to a user's contact Id`() =
        runTest {
            coEvery { readContactService.getByContactId(Aydan.graphqlId) } returns Aydan.entityPersisted
            val readEmailServiceImpl = ReadEmailServiceImpl(emailRepository, readDoctorService, readPatientService, readContactService)
            val emails = readEmailServiceImpl.getContactEmails(Aydan.graphqlId)

            assertThat(emails, containsInAnyOrder(Aydan.email.map { equalTo(it) }))
        }
}


@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
internal class ReadEmailServiceImplDataTest(
    @Autowired private val testEntityManager: TestEntityManager,
    @Autowired private val emailRepository: EmailRepository,
) {
    @MockkBean
    private lateinit var securityWebFilterChain: SecurityWebFilterChain

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @MockkBean
    private lateinit var readPatientService: ReadPatientService

    @MockkBean
    private lateinit var readContactService: ReadContactService

    @Test
    internal fun `check returns an email address when queried with an Id from the database with matching Id`() = runTest {
        val jennyEmailAddressId =
            testEntityManager.persistAndGetId(EmailMother.jennysEmail, UUID::class.java)
        val readEmailServiceImpl =
            ReadEmailServiceImpl(emailRepository, readDoctorService, readPatientService, readContactService)
        val returnedEmail = readEmailServiceImpl.getByEmailId(EmailDto.Id(jennyEmailAddressId!!))

        assertThat(returnedEmail, Matchers.notNullValue())
        assertThat(returnedEmail!!.id, equalTo(jennyEmailAddressId))
    }

    @Test
    internal fun `check returns null when the database has no matching Id corresponding to an email address`() =
        runTest {
            val someNonExistentUuid = UUID.fromString("235b4875-92d4-4553-8852-eb8f4b3a887d")
            val readEmailServiceImpl =
                ReadEmailServiceImpl(emailRepository, readDoctorService, readPatientService, readContactService)
            val returnedEmail = readEmailServiceImpl.getByEmailId(EmailDto.Id(someNonExistentUuid))

            assertThat(returnedEmail, nullValue())
        }
}
