package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.util.ClinicMother.DansClinic
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
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
        val newClinicName = "My Clinic"

        coEvery { updateClinicService.editClinic(match { it.cid.value == DansClinic.id }) } returns DansClinic.entityPersisted.apply { name = newClinicName }
        //language=graphql
        val mutation = """mutation { editClinic(clinic: {cid: {value: "${DansClinic.id}"}, name: "$newClinicName"}) { name } }"""
        graphQlTester
            .document(mutation)
            .execute()
            .errors().satisfy { assertThat(it, equalTo(emptyList())) }
            .path("editClinic.name").entity(String::class.java).satisfies { assertThat(it, equalTo(newClinicName)) }
    }
}