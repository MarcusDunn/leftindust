package com.leftindust.mockingbird.clinic

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ReadClinicServiceImplTest {
    @Test
    internal fun `check returns null when clinicById is called and no such clinic exists`(): Unit = runBlocking {
        val mockRepository = mockk<ClinicRepository>() {
            coEvery { findById(any()) } returns null
        }

        val readClinicService = ReadClinicServiceImpl(mockRepository, mockk())

        val result = readClinicService.getByClinicId(ClinicDto.ClinicDtoId(UUID.randomUUID()))

        assertEquals(null, result)

        coVerify(exactly = 1) { mockRepository.findById(any()) }
    }

    @Test
    internal fun `check does not return null when clinicById is called and a clinic exists with that id`(): Unit = runBlocking {
        val uuid = UUID.randomUUID()
        val clinic = mockk<Clinic>()

        val mockRepository = mockk<ClinicRepository>() {
            coEvery { findById(uuid) } returns clinic
        }

        val readClinicService = ReadClinicServiceImpl(mockRepository, mockk())

        val result = readClinicService.getByClinicId(ClinicDto.ClinicDtoId(uuid))

        assertEquals(clinic, result)

        coVerify (exactly = 1) { mockRepository.findById(any) }
    }
}