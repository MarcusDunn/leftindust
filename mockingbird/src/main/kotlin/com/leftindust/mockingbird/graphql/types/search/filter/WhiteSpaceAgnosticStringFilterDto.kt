package com.leftindust.mockingbird.graphql.types.search.filter

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.From
import jakarta.persistence.metamodel.SingularAttribute

class WhiteSpaceAgnosticStringFilterDto(
    eq: String? = null,
    ne: String? = null,
    contains: String? = null,
    notContain: String? = null,
    startsWith: String? = null,
    notStartWith: String? = null,
    endsWith: String? = null,
    notEndWith: String? = null,
    strict: Boolean,
) : AbstractStringFilter(
    eq = eq?.replace(" ", ""),
    ne = ne?.replace(" ", ""),
    contains = contains?.replace(" ", ""),
    notContain = notContain?.replace(" ", ""),
    startsWith = startsWith?.replace(" ", ""),
    notStartWith = notStartWith?.replace(" ", ""),
    endsWith = endsWith?.replace(" ", ""),
    notEndWith = notEndWith?.replace(" ", ""),
    strict = strict,
) {

    override fun <X, Z> normalizeColumn(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, String>
    ): Expression<String> {
        return criteriaBuilder.function(
            "REPLACE",
            String::class.java,
            root.get(columnName),
            criteriaBuilder.literal(" "),
            criteriaBuilder.literal("")
        )
    }
}