package com.leftindust.mockingbird.dao.impl

import com.google.gson.JsonObject
import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.form.FormData
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.form.HibernateFormDataRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.form.FormDataDaoImpl
import com.leftindust.mockingbird.patient.GraphQLPatient
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class FormDataDaoImplTest {
    private val authorizer = mockk<Authorizer> {
        coEvery { getAuthorization(any(), any()) } returns Authorization.Allowed
    }

    @Test
    fun `check attachForm success`(): Unit = runBlocking {
        val formData = mockk<FormData>()
        val formDataRepository = mockk<HibernateFormDataRepository> {
            every { save(any()) } returns formData
        }
        val uuid = UUID.randomUUID()
        val patient = mockk<Patient>()
        val patientRepository = mockk<HibernatePatientRepository> {
            every { getById(uuid) } returns patient
        }

        val formDataDao = FormDataDaoImpl(formDataRepository, patientRepository, authorizer)
        val result = formDataDao.attachForm(GraphQLPatient.ID(uuid), JsonObject(), mockk())
        assertEquals(formData, result)
    }

    @Test
    fun `check getForms success`(): Unit = runBlocking {
        val uuid = UUID.randomUUID()
        val formData = mockk<FormData>()
        val formDataRepository = mockk<HibernateFormDataRepository> {
            every { getByPatient_Id(uuid) } returns listOf(formData)
        }

        val formDataDao = FormDataDaoImpl(formDataRepository, mockk(), authorizer)
        val result = formDataDao.getForms(GraphQLPatient.ID(uuid), mockk())
        assertEquals(listOf(formData), result)
    }
}