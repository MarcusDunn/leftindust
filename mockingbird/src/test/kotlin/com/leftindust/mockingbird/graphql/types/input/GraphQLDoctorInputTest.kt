package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.doctor.GraphQLDoctorInput
import com.leftindust.mockingbird.person.GraphQLNameInfoInput
import com.leftindust.mockingbird.user.GraphQLUserInput
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class GraphQLDoctorInputTest {
    @Test
    internal fun `check can create doctor input with same user name info and doctor nameinfo`() {
        val graphQLUserInput = GraphQLUserInput("", GraphQLNameInfoInput("my first name", null, "my last name"))
        assertDoesNotThrow {
            GraphQLDoctorInput(graphQLUserInput, nameInfo = graphQLUserInput.nameInfo)
        }
    }

    @Test
    internal fun `check cannot create doctor input with different user name info and doctor nameinfo`() {
        val graphQLUserInput = GraphQLUserInput("", GraphQLNameInfoInput("my first name", null, "my last name"))
        assertThrows<IllegalArgumentException> {
            GraphQLDoctorInput(graphQLUserInput, nameInfo = graphQLUserInput.nameInfo.copy("my other first name"))
        }
    }
}