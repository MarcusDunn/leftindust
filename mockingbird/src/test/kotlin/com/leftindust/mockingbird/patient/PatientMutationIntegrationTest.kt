package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.MockingbirdApplication
import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.util.AddressMother
import com.leftindust.mockingbird.util.AddressMother.JennysHouse
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.EmailMother
import com.leftindust.mockingbird.util.PatientMother
import com.leftindust.mockingbird.util.PhoneMother
import com.ninjasquad.springmockk.MockkBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.test.context.ContextConfiguration

@OptIn(ExperimentalCoroutinesApi::class)
@AutoConfigureGraphQlTester
@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [MockingbirdApplication::class])
internal class PatientMutationIntegrationTest(
    @Autowired
    private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain
    @Test
    internal fun `check can add and update patient`() {
        @Language("graphql")
        val addMutation = """
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
                                    number: "+17781234567"
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
                    addresses {
                        addressType
                        address
                        country
                    }
                    dateOfBirth
                    insuranceNumber
                    sex
                    gender
                    ethnicity                  
                }                        
            }
        """.trimIndent()


        val addedPatient: PatientDto = graphQlTester.document(addMutation)
            .execute()
            .errors()
            .verify()
            .path("addPatient")
            .entity(PatientDto::class.java)
            .matches {
                it.dateOfBirth.isEqual(PatientMother.Dan.dto.dateOfBirth)
                it.sex == PatientMother.Dan.dto.sex
            }
            .path("addPatient.firstName")
            .entity(String::class.java)
            .matches {
                it == PatientMother.Dan.firstName
            }
            .path("addPatient.addresses[0].country")
            .entity(Countries::class.java)
            .isEqualTo(Countries.Canada)
            .path("addPatient")
            .entity(PatientDto::class.java).get()

        @Language("graphql")
        val updateMutation = """
            mutation {
                editPatient(editPatient: {
                    pid: { value: "${addedPatient.id.value}" }
                    nameInfo: {
                        firstName: "Dann"
                        middleName: "TheDan"
                        lastName: "Servershani"
                    }
                    addresses: [{
                        address: "${JennysHouse.createDto.address}"
                        addressType: Home
                        city: "${JennysHouse.createDto.city}"
                        province: "${JennysHouse.createDto.province}"
                        country: Canada
                        postalCode: "${JennysHouse.createDto.postalCode}"
                    }]
                    phones: []
                    emails: []
                    insuranceNumber: ""
                    dateOfBirth: "${DoctorMother.Jenny.dateOfBirth}"
                    sex: Female
                    gender: ""
                    ethnicity: Asian
                    emergencyContacts: []
                    doctors: []
                    thumbnail: null
                })
                {
                    id { value }
                    firstName
                    middleName
                    lastName
                    addresses {
                        id { value }
                        city
                        country
                        province
                        address
                        postalCode
                        addressType
                    }
                    dateOfBirth
                    insuranceNumber
                    sex
                    gender
                    ethnicity
                }
            }
        """.trimIndent()

        graphQlTester.document(updateMutation)
            .execute()
            .errors()
            .verify()
            .path("editPatient")
            .entity(PatientDto::class.java)
            .matches {
                it.dateOfBirth.isEqual(DoctorMother.Jenny.dto.dateOfBirth)
                it.sex == Sex.Female
            }
            .path("editPatient.firstName")
            .entity(String::class.java)
            .matches {
                it == "Dann"
            }
            .path("editPatient.addresses[0]")
            .entity(AddressDto::class.java)
            .matches { it.city == JennysHouse.dto.city }
    }
}