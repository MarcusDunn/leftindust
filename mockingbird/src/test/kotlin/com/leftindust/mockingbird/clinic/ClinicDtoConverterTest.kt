package com.leftindust.mockingbird.clinic

import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ClinicDtoConverterTest {
    private val clinicDtoConverter = ClinicDtoConverter()

    @Test
    internal fun `check keeps same id`() {
        val clinicUuid = UUID.randomUUID()
        val result = clinicDtoConverter.convertToDto(mockk(relaxed = true) {
            every { id } returns clinicUuid;
        })
        assertEquals(clinicUuid, result.id.value)
    }

    @Test
    internal fun `check keeps same name`() {
        val clinicName = "my clinic name"
        val result = clinicDtoConverter.convertToDto(mockk(relaxed = true) {
            every { name } returns clinicName
        })
        assertEquals(clinicName, result.name)
    }
}