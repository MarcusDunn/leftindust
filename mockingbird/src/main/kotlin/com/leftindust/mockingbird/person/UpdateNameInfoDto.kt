package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable

data class UpdateNameInfoDto(
    override val firstName: Updatable<String>,
    override val middleName: Deletable<String>,
    override val lastName: Updatable<String>,
) : UpdateNameInfo

