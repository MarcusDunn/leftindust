package com.leftindust.mockingbird.validate

import dev.forkhandles.result4k.onFailure
import dev.forkhandles.values.parseResult4k
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.fail

internal class EmailAddressTest {
    @TestFactory
    internal fun valid() = listOf(
        "email@example.com",
        "firstname.lastname@example.com",
        "email@subdomain.example.com",
        "firstname+lastname@example.com",
        "email@123.123.123.123",
        "email@[123.123.123.123]",
        "\"email\"@example.com",
        "1234567890@example.com",
        "email@example-one.com",
        "_______@example.com",
        "email@example.name",
        "email@example.museum",
        "email@example.co.jp",
        "firstname-lastname@example.com",
    ).map {
        dynamicTest("$it is a valid email") {
            EmailAddress.parseResult4k(it).onFailure { fail(it.reason) }
        }
    }

    @TestFactory
    internal fun invalid() = listOf(
        "plainaddress",
        "#@%^%#$@#$@#.com",
        "@example.com",
        "Joe Smith <email@example.com>",
        "email.example.com",
        "email@example@example.com",
        ".email@example.com",
        "email.@example.com",
        "email..email@example.com",
        "あいうえお@example.com",
        "email@example.com (Joe Smith)",
        "email@example",
        "email@-example.com",
        "Abc..123@example.com"
    ).map {
        dynamicTest("$it is an invalid email") {
            val success = EmailAddress.parseResult4k(it).onFailure { return@dynamicTest }
            fail(Exception("$success is not a valid email yet we accepted it"))
        }
    }
}