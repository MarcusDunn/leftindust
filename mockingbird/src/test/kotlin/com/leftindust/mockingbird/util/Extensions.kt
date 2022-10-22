package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.ConversionError
import com.leftindust.mockingbird.IntoMockingbirdException
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.onFailure

fun <T, E: IntoMockingbirdException> Result4k <T,E>.valueOrThrow(): T {
    return onFailure { throw it.reason.toMockingbirdException()}
}

