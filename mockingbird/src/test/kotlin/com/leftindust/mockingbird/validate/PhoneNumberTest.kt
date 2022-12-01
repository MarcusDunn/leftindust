package com.leftindust.mockingbird.validate

import com.leftindust.mockingbird.util.PhoneMother
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
        "+12125551212",
        "+12368083995",
        "+23051234567",
        "+17782111992",
        PhoneMother.JennysHomePhone.number,
        PhoneMother.JennysWorkPhone.number,
        PhoneMother.DansCell.number
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
        "1+2125551212",
        "-12368083995",
        "kelvin@leftindust.com",
        "+2",
        "778-990;1234"
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