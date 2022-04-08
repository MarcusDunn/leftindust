package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.graphql.types.GraphQLMonth
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month
import java.util.*

internal class GraphQLDateInputTest {
    @Test
    internal fun `check toLocalDate`() {
        assertEquals(GraphQLDateInput(9, GraphQLMonth.Mar, 2022).toLocalDate(), LocalDate.of(2022, Month.MARCH, 9))
    }

    @Test
    internal fun `check toDate`() {
        assertEquals(
            GraphQLDateInput(9, GraphQLMonth.Mar, 2022).toDate(),
            Date.from(GregorianCalendar(2022, 2, 9, 0, 0, 0).toInstant())
        )
    }
}