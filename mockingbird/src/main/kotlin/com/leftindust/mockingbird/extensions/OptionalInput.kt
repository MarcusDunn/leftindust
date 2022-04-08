package com.leftindust.mockingbird.extensions

import com.expediagroup.graphql.generator.execution.OptionalInput

/**
 * convenience function to turn graphql kotlin optional input types to a default value
 * @param default the default value of the optional input in the case it is undefined
 * @returns either the non-null value defined in OptionalInput or the default passed in
 */
fun <T> OptionalInput<T>.getOrDefault(default: T): T {
    return when (this) {
        is OptionalInput.Defined -> this.value ?: default
        is OptionalInput.Undefined -> default
    }
}

fun <T> OptionalInput<T>.getOrThrow(throwable: Throwable = RuntimeException("called getOrThrow on Undefined Input")): T {
    return when (this) {
        is OptionalInput.Defined -> this.value ?: throw throwable
        is OptionalInput.Undefined -> throw throwable
    }
}

fun <T> OptionalInput<T>.getOrNull(): T? {
    return getOrDefault(null)
}

/**
 * sets the value for undefined things while still keeping the possibility of a defined `null`
 */
fun <T> OptionalInput<T>.onUndefined(onUndefined: T?): T? {
    return when (this) {
        is OptionalInput.Defined -> this.value
        is OptionalInput.Undefined -> onUndefined
    }
}