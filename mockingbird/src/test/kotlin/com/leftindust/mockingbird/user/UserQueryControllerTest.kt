package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.person.NameInfoEntity
import com.leftindust.mockingbird.util.MediqUserMother.Marcus
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.security.web.server.SecurityWebFilterChain

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class MediqUserQueryControllerUnitTest {
    @MockK
    private lateinit var readMediqUserService: ReadMediqUserService
    private val mediqUserToMediqUserDtoConverter = MediqUserToMediqUserDtoConverter(MediqGroupToMediqGroupDtoConverter())


    @Test
    internal fun `check returns null if no such user exists`() = runTest {
        val mediqUserQueryController = MediqUserQueryController(readMediqUserService, mediqUserToMediqUserDtoConverter)

        // if the user does not exist
        coEvery { readMediqUserService.getByUserUid(any()) } returns null

        // and then we request for one
        val userByUserUniqueId = mediqUserQueryController.userByUserUniqueId("hello world")

        // then the controller returns null
        assertThat(userByUserUniqueId, nullValue())
    }

    @Test
    internal fun `check that we return a user with the same id if the user exists`() = runTest {
         val mediqUserQueryController = MediqUserQueryController(readMediqUserService, mediqUserToMediqUserDtoConverter)

        // if the user exists
        val mediqUser = MediqUser(
            uniqueId = "hello world",
            group = null,
            nameInfoEntity = NameInfoEntity("Marcus", "Dunn", null)
        )

        coEvery { readMediqUserService.getByUserUid("hello world") } returns mediqUser

        // and then we request for one
        val userByUserUniqueId = mediqUserQueryController.userByUserUniqueId("hello world")

        // then the user is not null and the id is equal to the mediq user the readUserService returned
        assertThat(userByUserUniqueId, notNullValue())
        assertThat(userByUserUniqueId!!.id.value, equalTo(mediqUser.uniqueId))
    }
}

@GraphQlTest(controllers = [MediqUserQueryController::class])
internal class MediqUserQueryControllerWebTest(
    @Autowired private val graphQlTester: GraphQlTester,
) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @MockkBean
    private lateinit var readMediqUserService: ReadMediqUserService

    @Test
    internal fun `check can query by id`() {
        coEvery { readMediqUserService.getByUserUid(Marcus.uniqueId) } returns Marcus.domain

        @Language("graphql")
        val query = """
            query {
                userByUserUniqueId(uniqueId: "${Marcus.uniqueId}") {
                    id { value }
                    group {
                        id { value }
                        name
                    }                        
                }
            }
            
        """.trimIndent()

        graphQlTester.document(query)
            .execute()
            .errors()
            .verify()
            .path("userByUserUniqueId")
            .entity(MediqUserDto::class.java)
            .isEqualTo(Marcus.dto)
    }
}