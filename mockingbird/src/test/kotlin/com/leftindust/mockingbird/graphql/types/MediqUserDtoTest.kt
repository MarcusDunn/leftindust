package com.leftindust.mockingbird.graphql.types

import com.google.firebase.auth.UserRecord
import com.leftindust.mockingbird.auth.*
import com.leftindust.mockingbird.person.ReadNameInfoService
import com.leftindust.mockingbird.user.UserDao
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.patient.ReadPatientService
import com.leftindust.mockingbird.user.UserFetcher
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.person.NameInfoDto
import com.leftindust.mockingbird.user.GraphQLFirebaseInfo
import com.leftindust.mockingbird.user.MediqUserDto
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class MediqUserDtoTest {

    @Test
    fun `isRegistered general success`() {

        val userDao = mockk<UserDao> {
            every { findUserByUid("uid", any()) } returns mockk()
        }

        val mediqUserDto = runBlocking { MediqUserDto("uid", null).isRegistered(userDao, MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals(true, mediqUserDto)
    }

    @Test
    fun `isRegistered special success`() {

        val userDao = mockk<UserDao> {
            every { findUserByUid("uid", any()) } returns null
        }

        val mediqUserDto =
            runBlocking { MediqUserDto("uid", null).isRegistered(userDao, MockDataFetchingEnvironment.withDummyMediqToken) }
        assertEquals(false, mediqUserDto)
    }

    @Test
    fun `isRegistered general failure`() {
        val userDao = mockk<UserDao> {
            every { findUserByUid("uid", any()) } throws NotAuthorizedException(
                mockk(),
                Crud.READ to Tables.User
            )
        }

        assertThrows<NotAuthorizedException> {
            runBlocking { MediqUserDto("uid", null).isRegistered(userDao, MockDataFetchingEnvironment.withDummyMediqToken) }
        }
    }

    @Test
    fun firebaseUserInfo() {

        val expected = mockk<UserRecord>(relaxed = true)

        val userFetcher = mockk<UserFetcher> {
            every { getUserInfo("uid", any()) } returns expected
        }
        val result = runBlocking { MediqUserDto("uid", null).firebaseUserInfo(userFetcher, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(GraphQLFirebaseInfo(expected), result)
    }

    @Test
    fun permissions() {

        val authorizationDao = mockk<AuthorizationDao> {
            every { getRolesForUserByUid("uid") } returns emptyList()
        }

        val result = runBlocking { MediqUserDto("uid", null).permissions(authorizationDao) }

        assertEquals(GraphQLPermissions(emptyList()), result)
    }

    @Test
    fun hasPermission() {

        val authorizationDao = mockk<AuthorizationDao> {
            every { getRolesForUserByUid("uid") } returns listOf(mockk {
                every { action } returns Action(Crud.UPDATE to Tables.Patient)
            })
        }

        val result = runBlocking {
            MediqUserDto("uid", null).hasPermission(
                authorizationDao,
                GraphQLPermissionInput(referencedTableName = Tables.Patient, permissionType = Crud.UPDATE)
            )
        }

        assertEquals(true, result)
    }

    @Test
    fun patient() {

        val expectedPatient = EntityStore.patient("GraphqlUserTest.patient")
            .apply { id = UUID.nameUUIDFromBytes("GraphqlUserTest.patient".toByteArray()) }

        val mockkPatientDao = mockk<ReadPatientService> {
            every { getByUser("uid", any()) } returns expectedPatient
        }

        val result = runBlocking { MediqUserDto("uid", null).patient(mockkPatientDao, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(PatientDto(expectedPatient), result)
    }

    @Test
    internal fun names() {

        val nameInfo = mockk<NameInfo>(relaxed = true)

        val readNameInfoService = mockk<ReadNameInfoService> {
            every { getByUniqueId("uid", any()) } returns nameInfo
        }

        val result =
            runBlocking { MediqUserDto("uid", null).name(readNameInfoService, MockDataFetchingEnvironment.withDummyMediqToken) }

        assertEquals(NameInfoDto(nameInfo), result)
    }
}