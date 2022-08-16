package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.phone.PhoneDto
import com.leftindust.mockingbird.phone.ReadPhoneService
import com.leftindust.mockingbird.util.DoctorMother
import com.leftindust.mockingbird.util.PhoneMother
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

@GraphQlTest(controllers = [DoctorQueryController::class, DoctorPhoneQueryController::class])
internal class DoctorPhoneQueryControllerTest(
    @Autowired private val graphQlTester: GraphQlTester
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readPhoneService: ReadPhoneService

    @MockkBean
    private lateinit var readDoctorService: ReadDoctorService

    @Test
    internal fun `check can query for phone fields`() {
        coEvery { readDoctorService.getByDoctorId(DoctorMother.Dan.graphqlId) } returns DoctorMother.Dan.entityPersisted
        coEvery { readPhoneService.getByDoctorId(DoctorMother.Dan.graphqlId) } returns listOf(PhoneMother.DansCell.entityPersisted)

        @Language("graphql")
        val query = """
            query {
                doctorsByDoctorIds(doctorIds: [{ value: "${DoctorMother.Dan.id}" }]) {
                    phoneNumbers {
                        id { value }
                        number
                        type
                    }
                }
            }
        """.trimIndent()

        // TODO check that phone number and phone type also returned correctly

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("doctorsByDoctorIds[0].phoneNumbers[*].id.value")
            .entity(object : ParameterizedTypeReference<List<UUID>>() {})
            .matches { it.contains(PhoneMother.DansCell.dto.id.value) }
            .path("doctorsByDoctorIds[0].phoneNumbers[0]")
            .entity(PhoneDto::class.java)
            .isEqualTo(PhoneMother.DansCell.dto)

    }
}