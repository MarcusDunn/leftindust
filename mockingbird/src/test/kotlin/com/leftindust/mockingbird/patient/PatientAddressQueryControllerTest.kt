package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import com.leftindust.mockingbird.util.AddressMother
import com.leftindust.mockingbird.util.PatientMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

@GraphQlTest(controllers = [PatientQueryController::class, PatientAddressQueryController::class])
class PatientAddressQueryControllerTest(
        @Autowired private val graphQlTester: GraphQlTester
    ) {
        @MockkBean
        private lateinit var httpSecurity: SecurityWebFilterChain

        @MockkBean
        private lateinit var readPatientService: ReadPatientService

        @MockkBean
        private lateinit var readAddressService: ReadAddressService

        @Test
        internal fun `check can query for phone fields`() {
            coEvery { readPatientService.getByPatientId(PatientMother.Dan.graphqlId) } returns PatientMother.Dan.entityPersisted
            coEvery { readAddressService.getByPatientId(PatientMother.Dan.graphqlId) } returns listOf(AddressMother.DansHouse.entityPersisted)

            @Language("graphql")
            val query = """
            query {
                patientsByPatientId(patientIds: [{ value: "${PatientMother.Dan.id}" }]) {
                    addresses {
                        id { value }
                        type
                        address
                        city
                        country
                        province
                        postalCode
                    }
                }
            }
        """.trimIndent()


            graphQlTester.document(query)
                .execute()
                .errors()
                .verify()
                .path("patientsByPatientId[0].addresses[*].id.value")
                .entity(object : ParameterizedTypeReference<List<UUID>>() {})
                .matches { it.contains(AddressMother.DansHouse.dto.id.value) }
                .path("patientsByPatientId[0].addresses[0]")
                .entity(AddressDto::class.java)
                .isEqualTo(AddressMother.DansHouse.dto)

        }

}