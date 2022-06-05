package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.util.ClinicMother
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import java.util.UUID
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
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
        coEvery { updateClinicService.editClinic(any()) } returns ClinicMother.dansClinic.apply { name = "My Clinic" }
        //language=graphql
        val mutation = """mutation { editClinic(clinic: {cid: {value: "${UUID.randomUUID()}"}, name: "My Clinic"}) { name } }"""
        graphQlTester
            .document(mutation)
            .execute()
            .errors().satisfy { assertThat(it, equalTo(emptyList())) }
            .path("editClinic.name").entity(String::class.java).satisfies { assertThat(it, equalTo("My Clinic")) }
    }
}