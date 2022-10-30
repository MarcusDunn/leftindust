package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.MockingbirdApplication
import com.leftindust.mockingbird.util.AddressMother
import com.leftindust.mockingbird.util.EmailMother
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.PhoneMother
import com.ninjasquad.springmockk.MockkBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache
import org.springframework.boot.test.autoconfigure.graphql.AutoConfigureGraphQl
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
//@GraphQlTest(controllers = [PatientMutationController::class])
@AutoConfigureCache
@AutoConfigureJson
@AutoConfigureGraphQl
@AutoConfigureGraphQlTester
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DataJpaTest
@ContextConfiguration(classes = [MockingbirdApplication::class])
internal class PatientMutationIntegrationTest(
    @Autowired
    private val graphQlTester: GraphQlTester,
    @Autowired
    private val createPatientService: CreatePatientService
) {

    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

//    @Bean
//    private lateinit var createPatientService: CreatePatientService


    @Test
    internal fun `check can add and update patient`() {
        @Language("graphql")
        val mutation = """
            mutation {
                addPatient(createPatient: {
                    nameInfo: {
                        firstName: "${PatientMother.Dan.firstName}"
                        middleName: "${PatientMother.Dan.middleName}"
                        lastName: "${PatientMother.Dan.lastName}"
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
                    dateOfBirth: "${PatientMother.Dan.dateOfBirth}"
                    sex: Male
                    gender: "${PatientMother.Dan.gender}"
                    ethnicity: null
                    emergencyContacts: [
                        {
                            nameInfo: {
                                firstName: "Boris"
                                middleName: null
                                lastName: "Vasilchi"
                            }
                            relationship: Other
                            phones: [
                                {
                                    type: Cell
                                    number: "778-123-4567"
                                }
                            ]
                            emails: [
                                {
                                    email: "boris@example.com"
                                    type: Other
                                }
                            ]  
                        }
                    ]
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
            .matches { it.equals(PatientMother.Dan.dto.id.value) }
            .path("addPatient")
            .entity(PatientDto::class.java)
            .isEqualTo(PatientMother.Dan.dto)

    }
}