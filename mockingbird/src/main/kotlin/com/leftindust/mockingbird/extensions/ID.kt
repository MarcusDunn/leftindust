package com.leftindust.mockingbird.extensions

import com.expediagroup.graphql.generator.scalars.ID


fun gqlID(id: Number): ID = ID(id.toString())
