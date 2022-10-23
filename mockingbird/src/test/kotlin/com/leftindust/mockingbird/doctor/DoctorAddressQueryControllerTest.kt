package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.address.AddressDto
import com.leftindust.mockingbird.address.ReadAddressService
import com.leftindust.mockingbird.util.AddressMother
import com.leftindust.mockingbird.util.DoctorMother
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

@GraphQlTest(controllers = [DoctorQueryController::class, DoctorAddressesQueryController::class])
internal class DoctorAddressQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readAddressService: ReadAddressService

    @MockkBean()
    private lateinit var readDoctorService: ReadDoctorService

    @Test
    internal fun `check can query for doctor address fields`() {
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns DoctorMother.Jenny.domain
        coEvery { readAddressService.getByDoctorId(DoctorMother.Jenny.graphqlId) } returns listOf(AddressMother.JennysHouse.entityPersisted)

        @Language("graphql")
        val query = """
            query {
                doctorsByDoctorIds(doctorIds: [{ value: "${DoctorMother.Jenny.id}" }]) {
                    addresses {
                        id { value }
                        addressType
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
            .path("doctorsByDoctorIds[0].addresses[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(AddressMother.JennysHouse.id) }
            .path("doctorsByDoctorIds[0].addresses[0]")
            .entity(AddressDto::class.java)
            .isEqualTo(AddressMother.JennysHouse.dto)

    }
}