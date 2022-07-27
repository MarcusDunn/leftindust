package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.util.ClinicMother.DansClinic
import java.util.UUID
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.junit.jupiter.api.Test

internal class MapDelegatingUpdateClinicDtoTest {
    @Test
    internal fun `test creating all defined '`() {
        val clinicName = "Clyde's Clinic"
        val clinicAddress = DansClinic.address
        val clinicDoctors = listOf(UUID.randomUUID())

        val clinicDto: ClinicEdit = MapDelegatingUpdateClinicDto(mapOf(
            MapDelegatingUpdateClinicDto::cid.name to mapOf(ClinicDto.ClinicDtoId::value.name to UUID.randomUUID()),
                    MapDelegatingUpdateClinicDto::name.name to clinicName,
                    MapDelegatingUpdateClinicDto::address.name to clinicAddress,
                    MapDelegatingUpdateClinicDto::doctors.name to clinicDoctors,
        ))

        assertThat(clinicDto.name, instanceOf(Updatable.Update::class.java))
        assertThat(clinicDto.address, instanceOf(Updatable.Update::class.java))
        assertThat(clinicDto.doctors, instanceOf(Updatable.Update::class.java))

        assertThat(clinicDto.name as Updatable.Update, equalTo(Updatable.Update(clinicName)))
        assertThat(clinicDto.address as Updatable.Update, equalTo(Updatable.Update(clinicAddress)))
        assertThat(clinicDto.doctors as Updatable.Update, equalTo(Updatable.Update(clinicDoctors)))
    }

    @Test
    internal fun `test creating all undefined '`() {
        val clinicDto: ClinicEdit = MapDelegatingUpdateClinicDto(mapOf(
            MapDelegatingUpdateClinicDto::cid.name to mapOf(ClinicDto.ClinicDtoId::value.name to UUID.randomUUID()),
        ))

        assertThat(clinicDto.name, instanceOf(Updatable.Ignore::class.java))
        assertThat(clinicDto.address, instanceOf(Updatable.Ignore::class.java))
        assertThat(clinicDto.doctors, instanceOf(Updatable.Ignore::class.java))
    }

    @Test
    internal fun `test creating mix of defined and undefined '`() {
        val clinicName = "Clyde's Clinic"
        val clinicDoctors = listOf(UUID.randomUUID())

        val clinicDto: ClinicEdit = MapDelegatingUpdateClinicDto(mapOf(
            MapDelegatingUpdateClinicDto::cid.name to mapOf(ClinicDto.ClinicDtoId::value.name to UUID.randomUUID()),
            MapDelegatingUpdateClinicDto::name.name to clinicName,
            MapDelegatingUpdateClinicDto::doctors.name to clinicDoctors,
        ))

        assertThat(clinicDto.name, instanceOf(Updatable.Update::class.java))
        assertThat(clinicDto.address, instanceOf(Updatable.Ignore::class.java))
        assertThat(clinicDto.doctors, instanceOf(Updatable.Update::class.java))

        assertThat(clinicDto.name as Updatable.Update, equalTo(Updatable.Update(clinicName)))
        assertThat(clinicDto.doctors as Updatable.Update, equalTo(Updatable.Update(clinicDoctors)))
    }
}