package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.LocalDate
import java.util.Calendar

@GraphQLName("Date")
data class GraphQLDate(
    val day: Int,
    val month: GraphQLMonth,
    val year: Int,
) {
    constructor(date: LocalDate) : this(
        day = date.dayOfMonth,
        month = GraphQLMonth.fromJavaMonth(date.month),
        year = date.year
    )

    @GraphQLIgnore
    fun toLocalDate(): LocalDate {
        return LocalDate.of(year, month.toJavaMonth(), day)
    }

    @Deprecated("will be removed before 1.0", ReplaceWith("using the information I send you"))
    fun toUtcTime(): GraphQLUtcTime {
        return GraphQLUtcTime(Calendar.getInstance().apply { set(year, month.toJavaMonth().value, day) }.timeInMillis)
    }
}