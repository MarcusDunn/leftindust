package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.search.Filter
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.From
import javax.persistence.criteria.Predicate
import javax.persistence.metamodel.SingularAttribute

abstract class AbstractStringFilter(
    val eq: String? = null,
    val ne: String? = null,
    val contains: String? = null,
    val notContain: String? = null,
    val startsWith: String? = null,
    val notStartWith: String? = null,
    val endsWith: String? = null,
    val notEndWith: String? = null,
    override val strict: Boolean,
) : Filter<String> {

    override fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, String>
    ): Predicate {
        val column = normalizeColumn(criteriaBuilder, root, columnName)
        val predicates = listOfNotNull(
            eq?.let { criteriaBuilder.equal(column, eq) },
            ne?.let { criteriaBuilder.notEqual(column, ne) },
            contains?.let { criteriaBuilder.like(column, "%$contains%") },
            notContain?.let { criteriaBuilder.notLike(column, "%$notContain%") },
            startsWith?.let { criteriaBuilder.like(column, "$startsWith%") },
            notStartWith?.let { criteriaBuilder.notLike(column, "$notStartWith%") },
            endsWith?.let { criteriaBuilder.like(column, "%$endsWith") },
            notEndWith?.let { criteriaBuilder.notLike(column, "%$notEndWith") },
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }

    abstract fun <X, Z> normalizeColumn(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, String>
    ): Expression<String>
}