package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
@DataJpaTest
class UpdatePatientServiceImplTest(
    @Autowired
    private val updatePatientServiceImpl: UpdatePatientServiceImpl,
    private val readPatientServiceImpl: ReadPatientServiceImpl,
    private val createPatientServiceImpl: CreatePatientServiceImpl
) {

    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @Test
    internal suspend fun `check can actually update a patient`() {
        // TODO
        createPatientServiceImpl.addNewPatient(PatientMother.Dan.createPatient)
        val updatedPatient = updatePatientServiceImpl.update(PatientMother.Dan.updatePatientDto)
    }
}