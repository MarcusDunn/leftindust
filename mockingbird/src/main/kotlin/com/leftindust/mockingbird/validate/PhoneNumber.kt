package com.leftindust.mockingbird.validate

import dev.forkhandles.values.StringValueFactory
import dev.forkhandles.values.Value
import dev.forkhandles.values.regex

/**
 * Match using E.164 standard
 * Numbers should start with a "+"
 */
private const val phoneRegex = "^\\+[1-9]\\d{1,14}\$"

@JvmInline
value class PhoneNumber private constructor(override val value: String): Value<String> {
    companion object : StringValueFactory<PhoneNumber>(
        fn = ::PhoneNumber,
        validation = { phoneRegex.regex(it.lowercase()) },
    )
}