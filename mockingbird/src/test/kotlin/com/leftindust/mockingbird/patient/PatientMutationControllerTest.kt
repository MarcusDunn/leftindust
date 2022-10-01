package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.util.*
import com.leftindust.mockingbird.util.PatientMother.Dan
import com.ninjasquad.springmockk.MockkBean
import graphql.GraphQL
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@GraphQlTest(controllers = [PatientMutationController::class])
internal class PatientMutationControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createPatientService: CreatePatientService

    @MockkBean
    private lateinit var updatePatientService: UpdatePatientService

    @Test
    internal fun `check can create patient`() {
        coEvery { createPatientService.addNewPatient(any()) } returns Dan.domainEntityDetached

        @Language("graphql")
        val mutation = """
            mutation {
                addPatient(createPatient: {
                    nameInfo: {
                        firstName: "${Dan.firstName}"
                        middleName: "${Dan.middleName}"
                        lastName: "${Dan.lastName}"
                    }
                    addresses: [
                        {
                            addressType: Home
                            address: "${AddressMother.DansHouse.address}",
                            city: "${AddressMother.DansHouse.city}",
                            country: Canada,
                            province: "${AddressMother.DansHouse.province}",
                            postalCode: "${AddressMother.DansHouse.postalCode}"
                        }
                    ]
                    phones: [
                        {
                            number: "${PhoneMother.DansCell.number}"
                            type: Cell
                        }
                    ]       
                    emails: [
                        {
                            email: "${EmailMother.DansEmail.address}"
                            type: Work
                        }
                    ]                   
                    insuranceNumber: null
                    dateOfBirth: "${Dan.dateOfBirth}"
                    sex: Male
                    gender: "${Dan.gender}"
                    ethnicity: null
                    emergencyContacts: []
                    doctors: []
                    thumbnail: null
                })  
                {
                    id { value }
                    firstName
                    middleName
                    lastName
                    dateOfBirth
                    insuranceNumber
                    sex
                    gender
                    ethnicity                    
                }                        
            }
        """.trimIndent()

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()

            .path("addPatient.id.value")
            .entity(object : ParameterizedTypeReference<UUID>() {})
            .matches { it.equals(Dan.dto.id.value) }
            .path("addPatient")
            .entity(PatientDto::class.java)
            .isEqualTo(Dan.dto)
    }

    @Test
    internal fun `check update patient works properly`(){

        coEvery { updatePatientService.update(match{it.pid.value == Dan.id}) } returns Dan.updatedDomainEntityDetached

        //language=graphql
        // update fields to be different, clear lists.
        @Language("graphql")
        val mutation = """
            mutation {
                editPatient(editPatient: {
                    pid: {value: "${Dan.id}"}
                    nameInfo: {
                        firstName: "Dann"
                        middleName: "TheDan"
                        lastName: "Servershani"
                    }
                    addresses: []
                    phones: [
                        {
                            number: "${PhoneMother.DansCell.number}"
                            type: Cell
                        }
                    ]       
                    emails: [
                        {
                            email: "${EmailMother.DansEmail.address}"
                            type: Work
                        }
                    ]                   
                    insuranceNumber: null
                    dateOfBirth: "${Dan.dateOfBirth}"
                    sex: Male
                    gender: "${Dan.gender}"
                    ethnicity: null
                    emergencyContacts: []
                    doctors: []
                    thumbnail: null
                })  
                {
                    id { value }
                    firstName
                    middleName
                    lastName
                    dateOfBirth
                    insuranceNumber
                    sex
                    gender
                    ethnicity                    
                }                        
            }
        """.trimIndent()


        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()

            .path("editPatient.id.value")
            .entity(object : ParameterizedTypeReference<UUID>() {})
            .matches { it.equals(Dan.dto.id.value) }

            .path("editPatient")
            .entity(PatientDto::class.java)
            .isEqualTo(Dan.updatedDto)
    }

}