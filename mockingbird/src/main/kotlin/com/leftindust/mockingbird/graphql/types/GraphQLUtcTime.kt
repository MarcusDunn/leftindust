package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@GraphQLName("UtcTime")
data class GraphQLUtcTime(
    val unixMilliseconds: Long,
) {

    @GraphQLName("TimeZonedTime")
    data class TimeZonedTime(
        val timeZone: String,
        val unixMilliseconds: Long,
    )

    @GraphQLDescription(
        """
        the timezone string should follow the format from https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
        eg. America/Los_Angeles for British Columbia's time zone (generally referred to as PST)
        """
    )
    // this function makes ZERO sense. I have no clue why it works, the variable names are lies and im so sorry
    fun withRespectTo(timeZone: String): TimeZonedTime {
        val utc = ZoneId.of("UTC")
        val otherTimeZone = ZoneId.of(timeZone)

        val utcDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixMilliseconds), otherTimeZone)
        val zonedDateTime = utcDateTime.withZoneSameLocal(utc)

        return TimeZonedTime(
            timeZone,
            zonedDateTime.toEpochSecond() * 1000,
        )
    }

    @GraphQLIgnore
    fun toTimestamp() = Timestamp(unixMilliseconds)
    fun before(end: GraphQLUtcTime): Boolean {
        return this.toTimestamp().before(end.toTimestamp())
    }

    constructor(instant: Instant) : this(
        unixMilliseconds = instant.toEpochMilli()
    )

    constructor(timestamp: Timestamp) : this(timestamp.toInstant())
}