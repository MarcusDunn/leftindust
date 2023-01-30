package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.util.ClinicMother.DansClinic
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@GraphQlTest(controllers = [ClinicMutationController::class])
internal class ClinicMutationControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var serverHttpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createClinicService: CreateClinicService

    @MockkBean
    private lateinit var updateClinicService: UpdateClinicService

    @Test
    internal fun `test change clinic name is an accepted query`() {
        coEvery { updateClinicService.editClinic(match { it.cid.value == DansClinic.id }) } returns DansClinic.domain
        @Language("graphql")
        val mutation = """mutation {
            |    editClinic(clinic: {
            |        cid: { value: "${DansClinic.id}" },
            |        name: "${DansClinic.dansClinicName}"
            |        address: {
            |            address: "2583 Diamond Crescent"
            |            addressType: Work
            |            city: "Coquitlam"
            |            country: Canada
            |            postalCode: "V3E 2Z9"
            |            province: "BC"
            |        }
            |    }) {
            |        name
            |    } }""".trimMargin()
        graphQlTester
            .document(mutation)
            .execute()
            .errors().satisfy { assertThat(it, equalTo(emptyList())) }
            .path("editClinic.name").entity(String::class.java)
            .satisfies { assertThat(it, equalTo(DansClinic.dansClinicName)) }
    }
}