package com.leftindust.mockingbird.util.unit

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.extensions.Authorization

abstract class LenientAuthorizerUnitTest {
    val authorizer = object : Authorizer {
        override fun getAuthorization(action: Action, user: MediqToken) = Authorization.Allowed
    }
}