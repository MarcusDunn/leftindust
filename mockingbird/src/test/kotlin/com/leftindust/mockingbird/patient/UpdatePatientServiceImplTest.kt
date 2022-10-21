package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.AddressRepository
import com.leftindust.mockingbird.address.CreateAddressServiceImpl
import com.leftindust.mockingbird.contact.ContactRepository
import com.leftindust.mockingbird.contact.CreateContactServiceImpl
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.email.CreateEmailServiceImpl
import com.leftindust.mockingbird.email.EmailRepository
import com.leftindust.mockingbird.person.*
import com.leftindust.mockingbird.phone.CreatePhoneServiceImpl
import com.leftindust.mockingbird.phone.PhoneRepository
import com.leftindust.mockingbird.survey.link.SurveyLinkRepository
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.valueOrThrow
import com.ninjasquad.springmockk.MockkBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@DataJpaTest
internal class UpdatePatientServiceImplTest(
    @Autowired private val patientRepository: PatientRepository,
    @Autowired private val doctorRepository: DoctorRepository,
    @Autowired private val surveyLinkRepository: SurveyLinkRepository,
    @Autowired private val nameInfoRepository: NameInfoRepository,
    @Autowired private val emailRepository: EmailRepository,
    @Autowired private val addressRepository: AddressRepository,
    @Autowired private val phoneRepository: PhoneRepository,
    @Autowired private val contactRepository: ContactRepository,

    ) {

    private val createNameInfoService = CreateNameInfoServiceImpl(nameInfoRepository)
    private val createEmailService = CreateEmailServiceImpl(emailRepository)
    private val createAddressService = CreateAddressServiceImpl(addressRepository)
    private val createPhoneService = CreatePhoneServiceImpl(phoneRepository)
    private val createContactService = CreateContactServiceImpl(
        contactRepository,
        patientRepository,
        createPhoneService,
        createEmailService,
        createNameInfoService
    )
    private val updateNameInfoService = UpdateNameInfoServiceImpl(nameInfoRepository)
    private val patientEntityToPatientConverter = PatientEntityToPatientConverter()

    private val updatePatientServiceImpl = UpdatePatientServiceImpl(
        patientRepository,
        updateNameInfoService,
        patientEntityToPatientConverter,
        createEmailService,
        createAddressService,
        createPhoneService,
        createContactService,
        doctorRepository,
    )
    private val readPatientServiceImpl =
        ReadPatientServiceImpl(patientRepository, surveyLinkRepository, patientEntityToPatientConverter)
    private val createPatientServiceImpl = CreatePatientServiceImpl(
        patientRepository,
        createNameInfoService,
        createAddressService,
        createEmailService,
        createPhoneService,
        createContactService,
        doctorRepository,
        patientEntityToPatientConverter
    )

    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @Test
    internal fun `Check Update Patient returns null when missing the patient from db`() = runTest {
        val updatedPatient =
            updatePatientServiceImpl.update(PatientMother.Dan.updatePatientDto.toUpdatePatient().valueOrThrow())

        assertThat(updatedPatient, equalTo(null))
    }

    @Test
    internal fun `Check Update patient works on an actual patient in the db`() = runTest {

        val patientEntity = patientRepository.save(PatientMother.Dan.entityTransient)

        val updatingPatient =
            PatientMother.Dan.updatePatientDto.copy(pid = PatientDto.PatientDtoId(patientEntity.id!!)).toUpdatePatient()
                .valueOrThrow()

        val updatedPatient = updatePatientServiceImpl.update(updatingPatient)!!

        assertThat(updatedPatient.nameInfo.firstName, equalTo(PatientMother.Dan.updatePatientDto.nameInfo.firstName))
        assertThat(updatedPatient.nameInfo.lastName, equalTo(PatientMother.Dan.updatePatientDto.nameInfo.lastName))
        assertThat(updatedPatient.nameInfo.middleName, equalTo(PatientMother.Dan.updatePatientDto.nameInfo.middleName))

        assertThat(updatedPatient.nameInfo.firstName, not(equalTo(PatientMother.Dan.firstName)))
        assertThat(updatedPatient.nameInfo.lastName, not(equalTo(PatientMother.Dan.lastName)))
        assertThat(updatedPatient.nameInfo.middleName, not(equalTo(PatientMother.Dan.middleName)))

        assertThat(
            patientEntity.contacts.map { it.email.map { it.address } },
            equalTo(PatientMother.Dan.updatePatientDto.emergencyContacts.map { it.emails.map { it.email } })
        )
        assertThat(
            patientEntity.contacts.map { it.email.map { it.address } },
            not(equalTo(PatientMother.Dan.contacts.map { it.email.map { it.address } }))
        )

        assertThat(patientEntity.insuranceNumber, equalTo(PatientMother.Dan.updatePatientDto.insuranceNumber))
        assertThat(patientEntity.insuranceNumber, not(equalTo(PatientMother.Dan.insuranceNumber)))

        assertThat(updatedPatient, notNullValue())
    }

}