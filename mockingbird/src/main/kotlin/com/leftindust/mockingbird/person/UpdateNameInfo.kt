package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable

interface UpdateNameInfo {
    val firstName: Updatable<String>
    val middleName: Deletable<String>
    val lastName: Updatable<String>
}