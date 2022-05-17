package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.doctor.CreateDoctorDto
import com.leftindust.mockingbird.person.CreateNameInfoDto
import com.leftindust.mockingbird.user.CreateUserDto
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DoctorDtoInputTest {
    @Test
    internal fun `check can create doctor input with same user name info and doctor nameinfo`() {
        val createUserDto = CreateUserDto("", CreateNameInfoDto("my first name", null, "my last name"))
        assertDoesNotThrow {
            CreateDoctorDto(createUserDto, nameInfo = createUserDto.nameInfo)
        }
    }

    @Test
    internal fun `check cannot create doctor input with different user name info and doctor nameinfo`() {
        val createUserDto = CreateUserDto("", CreateNameInfoDto("my first name", null, "my last name"))
        assertThrows<IllegalArgumentException> {
            CreateDoctorDto(createUserDto, nameInfo = createUserDto.nameInfo.copy("my other first name"))
        }
    }
}