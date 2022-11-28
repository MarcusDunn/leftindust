package com.leftindust.mockingbird.graphql.types.search.filter

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.From
import jakarta.persistence.metamodel.SingularAttribute

class CaseAgnosticStringFilterDto(
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
    eq = eq?.uppercase(),
    ne = ne?.uppercase(),
    contains = contains?.uppercase(),
    notContain = notContain?.uppercase(),
    startsWith = startsWith?.uppercase(),
    notStartWith = notStartWith?.uppercase(),
    endsWith = endsWith?.uppercase(),
    notEndWith = notEndWith?.uppercase(),
    strict = strict,
) {

    override fun <X, Z> normalizeColumn(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, String>
    ): Expression<String> {
        return criteriaBuilder.upper(root.get(columnName))
    }
}