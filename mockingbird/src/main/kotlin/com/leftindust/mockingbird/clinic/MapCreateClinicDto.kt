package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddressDto
import com.leftindust.mockingbird.address.CreateAddressGraphQlDto
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.toUpdatable
import org.springframework.graphql.data.ArgumentValue

data class ArgumentValueUpdateClinicDto(
    val cid: ClinicDto.ClinicDtoId,
    val name: ArgumentValue<String> = ArgumentValue.omitted(),
    val address: ArgumentValue<CreateAddressGraphQlDto> = ArgumentValue.omitted(),
    val doctors: ArgumentValue<List<DoctorDto.DoctorDtoId>> = ArgumentValue.omitted(),
)

fun ArgumentValueUpdateClinicDto.toClinicEditDto(): ClinicEditDto {
    return ClinicEditDtoImpl(
        cid,
        name = name.toUpdatable(),
        address = address.toUpdatable(),
        doctors = doctors.toUpdatable(),
    )
}

data class ClinicEditDtoImpl(
    override val cid: ClinicDto.ClinicDtoId,
    override val name: Updatable<String>,
    override val address: Updatable<CreateAddressGraphQlDto>,
    override val doctors: Updatable<List<DoctorDto.DoctorDtoId>>,
) : ClinicEditDto

class MapDelegatingCreateAddressDto(val map: Map<String, Any?>) : CreateAddressDto {
    override val addressType: AddressType by map
    override val address: String by map
    override val city: String by map
    override val country: Countries by map
    override val province: String by map
    override val postalCode: String by map
}