package com.leftindust.mockingbird.validate

import dev.forkhandles.result4k.onFailure
import dev.forkhandles.values.parseResult4k
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.fail
import kotlin.random.Random

internal class PhoneNumberTest {
    private val random = Random(100)
    @TestFactory
    internal fun valid() = listOf(
        "12125551212",
        "12368083995",
    ).flatMap { allLower ->
        val randomCase = String(allLower.map { if (random.nextBoolean()) { it.uppercaseChar() } else { it }  }.toCharArray())
        listOf(
            dynamicTest("$allLower is a valid phone number") {
                PhoneNumber.parseResult4k(allLower).onFailure { fail(it.reason) }
            },
            dynamicTest("$randomCase is a valid phone number") {
                PhoneNumber.parseResult4k(randomCase).onFailure { fail(it.reason) }
            }
        )
    }

    @TestFactory
    internal fun invalid() = listOf(
        "plainaddress",
        "#@%^%#$@#$@#.com",
    ).flatMap { allLower ->
        val randomCase = String(allLower.map { if (random.nextBoolean()) { it.uppercaseChar() } else { it }  }.toCharArray())
        listOf(
            dynamicTest("$allLower is an invalid phone number") {
                val success = PhoneNumber.parseResult4k(allLower).onFailure { return@dynamicTest }
                fail(Exception("$success is not a valid phone number yet we accepted it"))
            },
            dynamicTest("$randomCase is an invalid phone number") {
                val success = PhoneNumber.parseResult4k(randomCase).onFailure { return@dynamicTest }
                fail(Exception("$success is not a valid phone number yet we accepted it"))
            }
        )
    }
}