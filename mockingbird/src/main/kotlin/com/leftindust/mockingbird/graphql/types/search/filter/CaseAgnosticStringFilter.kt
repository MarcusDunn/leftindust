package com.leftindust.mockingbird.graphql.types.search.filter

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.From
import javax.persistence.metamodel.SingularAttribute

class CaseAgnosticStringFilter(
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