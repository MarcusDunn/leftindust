package com.leftindust.mockingbird.user

import com.leftindust.mockingbird.person.NameInfo
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class UserQueryControllerUnitTest {
    @MockK
    private lateinit var readUserService: ReadUserService
    private val mediqUserToMediqUserDtoConverter = MediqUserToMediqUserDtoConverter(MediqGroupToUserGroupDtoConverter())


    @Test
    internal fun `check returns null if no such user exists`() = runTest {
        val userQueryController = UserQueryController(readUserService, mediqUserToMediqUserDtoConverter)

        // if the user does not exist
        coEvery { readUserService.getByUserUid(any()) } returns null

        // and then we request for one
        val userByUserUniqueId = userQueryController.userByUserUniqueId("hello world")

        // then the controller returns null
        assertThat(userByUserUniqueId, nullValue())
    }

    @Test
    internal fun `check that we return a user with the same id if the user exists`() = runTest {
         val userQueryController = UserQueryController(readUserService, mediqUserToMediqUserDtoConverter)

        // if the user exists
        val mediqUser = MediqUser(
            uniqueId = "hello world",
            group = null,
            nameInfo = NameInfo("Marcus", "Dunn", null)
        )

        coEvery { readUserService.getByUserUid("hello world") } returns mediqUser

        // and then we request for one
        val userByUserUniqueId = userQueryController.userByUserUniqueId("hello world")

        // then the user is not null and the id is equal to the mediq user the readUserService returned
        assertThat(userByUserUniqueId, notNullValue())
        assertThat(userByUserUniqueId!!.id.value, equalTo(mediqUser.uniqueId))
    }
}