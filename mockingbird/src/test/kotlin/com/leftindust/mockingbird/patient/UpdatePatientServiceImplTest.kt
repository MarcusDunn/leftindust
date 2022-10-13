package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import io.ktor.util.reflect.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.web.server.SecurityWebFilterChain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
@DataJpaTest
class UpdatePatientServiceImplTest(

    private val updatePatientServiceImpl: UpdatePatientServiceImpl,
    private val readPatientServiceImpl: ReadPatientServiceImpl,
    private val createPatientServiceImpl: CreatePatientServiceImpl
) {

    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @Test
    internal suspend fun `check update patient works properly '`(){

        TODO()
//        createPatientServiceImpl.addNewPatient(PatientMother.Dan.createPatient)
//        val updatedPatient = updatePatientServiceImpl.update(PatientMother.Dan.updatePatientDto)
//
//        checkNotNull(updatedPatient)
//        val readPatient = readPatientServiceImpl.getByPatientId(PatientMother.Dan.updatePatientDto.pid)
//
//        assertThat(updatedPatient.dateOfBirth, equalTo(PatientMother.Dan.updatePatientDto.dateOfBirth))
//        assertThat(readPatient, equalTo(updatedPatient.id))
    }
}