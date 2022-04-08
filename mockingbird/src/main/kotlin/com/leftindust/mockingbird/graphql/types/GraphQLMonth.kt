package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import java.time.Month

@GraphQLName("Month")
enum class GraphQLMonth {
    Jan,
    Feb,
    Mar,
    Apr,
    May,
    Jun,
    Jul,
    Aug,
    Sep,
    Oct,
    Nov,
    Dec;

    @GraphQLIgnore
    fun toJavaMonth(): Month = when (this) {
        Jan -> Month.JANUARY
        Feb -> Month.FEBRUARY
        Mar -> Month.MARCH
        Apr -> Month.APRIL
        May -> Month.MAY
        Jun -> Month.JUNE
        Jul -> Month.JULY
        Aug -> Month.AUGUST
        Sep -> Month.SEPTEMBER
        Oct -> Month.OCTOBER
        Nov -> Month.NOVEMBER
        Dec -> Month.DECEMBER
    }

    companion object {
        fun fromJavaMonth(month: Month): GraphQLMonth = when (month) {
            Month.JANUARY -> Jan
            Month.FEBRUARY -> Feb
            Month.MARCH -> Mar
            Month.APRIL -> Apr
            Month.MAY -> May
            Month.JUNE -> Jun
            Month.JULY -> Jul
            Month.AUGUST -> Aug
            Month.SEPTEMBER -> Sep
            Month.OCTOBER -> Oct
            Month.NOVEMBER -> Nov
            Month.DECEMBER -> Dec
        }
    }
}