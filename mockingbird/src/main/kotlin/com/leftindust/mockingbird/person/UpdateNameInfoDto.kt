package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.address.CreateAddress
import com.leftindust.mockingbird.country.Countries
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.DeletableMapDelegate
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate

class MapDelegatingUpdateNameInfoDto(val map: Map<String, Any?>) : UpdateNameInfoDto {
    override val firstName: Updatable<String> by UpdatableMapDelegate(map)
    override val middleName: Deletable<String> by DeletableMapDelegate(map)
    override val lastName: Updatable<String> by UpdatableMapDelegate(map)
}

interface UpdateNameInfoDto : UpdateNameInfo {
    override val firstName: Updatable<String>
    override val middleName: Deletable<String>
    override val lastName: Updatable<String>
}