package com.leftindust.mockingbird.user

import com.google.firebase.auth.FirebaseAuth
import com.leftindust.mockingbird.util.MediqUserMother.Marcus
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.mockk
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

@GraphQlTest(controllers = [MediqUserMutationController::class])
internal class MediqUserMutationControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var createMediqUserService: CreateMediqUserService

    @MockkBean
    private lateinit var firebaseAuth: FirebaseAuth

    @Test
    internal fun `check can query by id`() {
        coEvery { firebaseAuth.getUser(Marcus.uniqueId) } returns mockk()
        val answer = Marcus.domain
        coEvery { createMediqUserService.addUser(Marcus.create) } returns answer

        @Language("graphql")
        val mutation = """
            mutation {
                createMediqUser(createUser: {
                    nameInfo: {
                        firstName: "${Marcus.firstName}"
                        lastName: "${Marcus.lastName}"
                        middleName: ${Marcus.middleName}
                    }
                    uid: "${Marcus.uniqueId}"
                }) {
                    id { value }
                    group {
                        id { value }
                        name
                    }
                }
            }
        """.trimIndent()

        graphQlTester.document(mutation)
            .execute()
            .errors()
            .verify()
            .path("createMediqUser")
            .entity(MediqUserDto::class.java)
            .isEqualTo(Marcus.dto)
    }
}