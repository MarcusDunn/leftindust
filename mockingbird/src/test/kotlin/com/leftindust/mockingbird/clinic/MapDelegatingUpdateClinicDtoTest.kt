package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.util.AddressMother.DansHouse
import java.util.UUID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.jupiter.api.Test
import org.springframework.graphql.data.ArgumentValue

internal class MapDelegatingUpdateClinicDtoTest {
    @Test
    internal fun `test creating all defined '`() {
        val clinicName = "Clyde's Clinic"
        val clinicAddress = DansHouse.createDto
        val clinicDoctors = listOf(UUID.randomUUID()).map { DoctorDto.DoctorDtoId(it) }

        val clinicDto: ClinicEditDto = ArgumentValueUpdateClinicDto(
            cid = ClinicDto.ClinicDtoId(UUID.randomUUID()),
            name = ArgumentValue.ofNullable(clinicName),
            address = ArgumentValue.ofNullable(clinicAddress),
            doctors = ArgumentValue.ofNullable(clinicDoctors),
        ).toClinicEditDto()

        assertThat(clinicDto.name, instanceOf(Updatable.Update::class.java))
        assertThat(clinicDto.address, instanceOf(Updatable.Update::class.java))
        assertThat(clinicDto.doctors, instanceOf(Updatable.Update::class.java))

        assertThat(clinicDto.name as Updatable.Update, equalTo(Updatable.Update(clinicName)))
        assertThat(clinicDto.address as Updatable.Update, equalTo(Updatable.Update(clinicAddress)))
        assertThat(clinicDto.doctors as Updatable.Update, equalTo(Updatable.Update(clinicDoctors)))
    }

    @Test
    internal fun `test creating all undefined '`() {
        val clinicDto: ClinicEditDto = ArgumentValueUpdateClinicDto(
            cid = ClinicDto.ClinicDtoId(UUID.randomUUID()),
        ).toClinicEditDto()

        assertThat(clinicDto.name, instanceOf(Updatable.Ignore::class.java))
        assertThat(clinicDto.address, instanceOf(Updatable.Ignore::class.java))
        assertThat(clinicDto.doctors, instanceOf(Updatable.Ignore::class.java))
    }

    @Test
    internal fun `test creating mix of defined and undefined '`() {
        val clinicName = "Clyde's Clinic"
        val clinicDoctors = listOf(UUID.randomUUID()).map { DoctorDto.DoctorDtoId(it) }

        val clinicDto: ClinicEditDto = ArgumentValueUpdateClinicDto(
            cid = ClinicDto.ClinicDtoId(UUID.randomUUID()),
            name = ArgumentValue.ofNullable(clinicName),
            doctors = ArgumentValue.ofNullable(clinicDoctors),
        ).toClinicEditDto()

        assertThat(clinicDto.name, instanceOf(Updatable.Update::class.java))
        assertThat(clinicDto.address, instanceOf(Updatable.Ignore::class.java))
        assertThat(clinicDto.doctors, instanceOf(Updatable.Update::class.java))

        assertThat(clinicDto.name as Updatable.Update, equalTo(Updatable.Update(clinicName)))
        assertThat(clinicDto.doctors as Updatable.Update, equalTo(Updatable.Update(clinicDoctors)))
    }
}