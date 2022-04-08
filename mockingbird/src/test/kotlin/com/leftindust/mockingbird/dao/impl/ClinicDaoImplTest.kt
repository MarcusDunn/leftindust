package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.entity.Clinic
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.dao.impl.repository.HibernateClinicRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLCountry
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicInput
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import java.util.UUID
import javax.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ClinicDaoImplTest {
    private val clinicRepository = mockk<HibernateClinicRepository>()
    private val doctorRepository = mockk<HibernateDoctorRepository>()
    private val entityManager = mockk<EntityManager>()
    private val authorizer = mockk<Authorizer>()

    @Test
    fun addClinic() {
        val doctorID = UUID.randomUUID()


        val mockkClinic = mockk<Clinic>()

        every { clinicRepository.save(any()) } returns mockkClinic

        every { entityManager.find(Doctor::class.java, doctorID) } returns mockk()


        val requester = mockk<MediqToken>()

        coEvery { authorizer.getAuthorization(any(), requester) } returns Authorization.Allowed

        val mockkGqlClinicInput = GraphQLClinicInput(
            name = "name",
            address = mockk(relaxed = true) {
                every { country } returns GraphQLCountry.Canada
                every { province } returns "BritishColumbia"
            },
            doctors = listOf(GraphQLDoctor.ID(doctorID))
        )

        val clinicDao = ClinicDaoImpl(clinicRepository, doctorRepository, entityManager, authorizer)

        val result = clinicDao.addClinic(mockkGqlClinicInput, requester)

        assertEquals(mockkClinic, result)
    }

    @Test
    fun `addClinic with insufficient perms`() {
        val doctorID = UUID.randomUUID()

        val mockkClinic = mockk<Clinic>()

        every { clinicRepository.save(any()) } returns mockkClinic

        every { entityManager.find(Doctor::class.java, 10L) } returns mockk()

        val mockkGqlClinicInput = GraphQLClinicInput(
            name = "name",
            address = mockk(relaxed = true) {
                every { country } returns GraphQLCountry.Canada
                every { province } returns "BritishColumbia"
            },
            doctors = listOf(GraphQLDoctor.ID(doctorID))
        )

        val requester = mockk<MediqToken>()

        coEvery { authorizer.getAuthorization(any(), requester) } returns Authorization.Denied

        val clinicDao = ClinicDaoImpl(clinicRepository, doctorRepository, entityManager, authorizer)

        assertThrows<NotAuthorizedException> {
            clinicDao.addClinic(mockkGqlClinicInput, requester)
        }
    }

    @Test
    fun editClinic() {
        val requester = mockk<MediqToken>()

        coEvery { authorizer.getAuthorization(any(), requester) } returns Authorization.Allowed

        val clinicId = UUID.randomUUID()
        val mockkGqlClinicInput = GraphQLClinicEditInput(
            cid = GraphQLClinic.ID(clinicId),
            address = mockk(relaxed = true) {
                every { country } returns GraphQLCountry.Canada
                every { province } returns "BritishColumbia"
            },
        )

        val mockkClinic = mockk<Clinic> {
            every { setByGqlInput(mockkGqlClinicInput, any()) } just runs
        }

        every { clinicRepository.getById(clinicId) } returns mockkClinic

        val clinicDao = ClinicDaoImpl(clinicRepository, doctorRepository, entityManager, authorizer)

        val result = clinicDao.editClinic(mockkGqlClinicInput, requester)

        assertEquals(mockkClinic, result)
    }

    @Test
    fun getByDoctor() {
        val doctorID = UUID.randomUUID()

        val requester = mockk<MediqToken>()

        every { authorizer.getAuthorization(any(), any()) } returns Authorization.Allowed

        val mockkClinic = mockk<Clinic>()

        val mockkDoctor = mockk<Doctor> {
            every { clinics } returns mutableSetOf(mockkClinic)
        }

        every { doctorRepository.getById(doctorID) } returns mockkDoctor

        val clinicDao = ClinicDaoImpl(clinicRepository, doctorRepository, entityManager, authorizer)

        val result = clinicDao.getByDoctor(GraphQLDoctor.ID(doctorID), requester)

        assertEquals(mutableSetOf(mockkClinic), result)
    }
}
