package com.leftindust.mockingbird.extensions

/**
 * class denoting the result of an authorization request
 */
enum class Authorization {
    Allowed,
    Denied,
}

fun Authorization.isAllowed(): Boolean {
    return when (this) {
        Authorization.Allowed -> true
        Authorization.Denied -> false
    }
}

fun Authorization.isDenied(): Boolean {
    return when (this) {
        Authorization.Allowed -> false
        Authorization.Denied -> true
    }
}
