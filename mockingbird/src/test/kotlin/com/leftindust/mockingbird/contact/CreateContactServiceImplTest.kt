package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmailServiceImpl
import com.leftindust.mockingbird.email.EmailRepository
import com.leftindust.mockingbird.patient.PatientRepository
import com.leftindust.mockingbird.person.CreateNameInfoServiceImpl
import com.leftindust.mockingbird.person.NameInfoRepository
import com.leftindust.mockingbird.phone.CreatePhoneServiceImpl
import com.leftindust.mockingbird.phone.Phone
import com.leftindust.mockingbird.phone.PhoneRepository
import com.leftindust.mockingbird.util.ContactMother
import com.leftindust.mockingbird.util.EmailMother
import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.web.server.SecurityWebFilterChain
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.fail
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.springframework.stereotype.Component

@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
@ExtendWith(MockKExtension::class)
internal class CreateContactServiceImplTest(
    @Autowired
    private val contactRepository: ContactRepository,
    @Autowired
    private val phoneRepository: PhoneRepository,
    @Autowired
    private val emailRepository: EmailRepository,
    @Autowired
    private val nameInfoRepository: NameInfoRepository,
) {

    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @Test
    internal fun `check can save entity`() = runTest {
        val createPhoneService = CreatePhoneServiceImpl(phoneRepository)
        val createEmailService = CreateEmailServiceImpl(emailRepository)
        val createNameInfoService = CreateNameInfoServiceImpl(nameInfoRepository)
        val createContactService = CreateContactServiceImpl(
            contactRepository,
            createPhoneService,
            createEmailService,
            createNameInfoService
        )

        val newContact = createContactService.createContact(ContactMother.Aydan.createDomain)
        MatcherAssert.assertThat(contactRepository.findByIdOrNull(newContact.id ?: fail("null id")), Matchers.notNullValue())
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class CreateContactServiceDatabaseTest {

    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockK
    private lateinit var contactRepository: ContactRepository

    @MockK
    private lateinit var phoneRepository: PhoneRepository

    @MockK
    private lateinit var emailRepository: EmailRepository

    @MockK
    private lateinit var nameInfoRepository: NameInfoRepository

    @Test
    internal fun `check can create contact entity`() = runTest {
        every { contactRepository.save(any()) } returns ContactMother.Aydan.entityDetached
        every { phoneRepository.save(any()) } returns mockk()
        every { emailRepository.save(any()) } returns EmailMother.DansEmail.entityDetached
        every { nameInfoRepository.save(any()) } returns mockk()

        val createPhoneService = CreatePhoneServiceImpl(phoneRepository)
        val createEmailService = CreateEmailServiceImpl(emailRepository)
        val createNameInfoService = CreateNameInfoServiceImpl(nameInfoRepository)
        val createContactService = CreateContactServiceImpl(
            contactRepository,
            createPhoneService,
            createEmailService,
            createNameInfoService
        )

        val contact = createContactService.createContact(ContactMother.Aydan.createDomain)
        verify(exactly = 1) { contactRepository.save(any()) }
    }


}