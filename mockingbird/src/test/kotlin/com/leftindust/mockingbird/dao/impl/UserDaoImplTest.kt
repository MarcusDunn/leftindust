package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.user.MediqUser
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.group.HibernateGroupRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.user.HibernateUserRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.user.CreateUserDto
import com.leftindust.mockingbird.util.EntityStore
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserDaoImplTest {
    private val authorizer = mockk<Authorizer>()
    private val userRepository = mockk<HibernateUserRepository>()
    private val groupRepository = mockk<HibernateGroupRepository>()
    private val doctorRepository = mockk<DoctorRepository>()
    private val patientRepository = mockk<HibernatePatientRepository>()

    @Test
    fun getUserByUid() {
        val mockkUser = mockk<MediqUser>()
        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed
        every { userRepository.getByUniqueId("test uid") } returns mockkUser

        val userDaoImpl = UserDaoImpl(authorizer, userRepository, groupRepository, doctorRepository, patientRepository)

        val actual = userDaoImpl.getUserByUid("test uid", mockk())

        assertEquals(mockkUser, actual)
    }

    @Test
    fun addUser() {
        val mockkUser = mockk<CreateUserDto>(relaxed = true) {
            every { uid } returns "uid"
            every { group } returns null
            every { doctor } returns null
        }
        val mockkMediqUser = mockk<MediqUser>()
        every { userRepository.save(any()) } returns mockkMediqUser

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val userDaoImpl = UserDaoImpl(authorizer, userRepository, groupRepository, doctorRepository, patientRepository)

        val actual = userDaoImpl.addUser(mockkUser, mockk())

        assertEquals(mockkMediqUser, actual)
    }

    @Test
    fun findByPatient() {
        val pid = PatientDto.PatientDtoId(UUID.nameUUIDFromBytes("kuyvl".toByteArray()))

        coEvery { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val expected = EntityStore.user("UserDaoImplTest.findByPatient")

        every { patientRepository.getById(pid.id) } returns EntityStore.patient("UserDaoImplTest.findByPatient").apply {
            user = expected
        }

        val userDaoImpl = UserDaoImpl(authorizer, userRepository, groupRepository, doctorRepository, patientRepository)

        val actual = userDaoImpl.findPatientUser(pid, mockk())

        assertEquals(expected, actual)
    }
}