package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.search.Filter
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.From
import javax.persistence.criteria.Predicate
import javax.persistence.metamodel.SingularAttribute

data class FloatFilter(
    val gt: Float? = null,
    val lt: Float? = null,
    override val strict: Boolean,
) : Filter<Float> {
    override fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, Float>
    ): Predicate {
        val predicates = listOfNotNull(
            gt?.let { criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), gt) },
            lt?.let { criteriaBuilder.lessThanOrEqualTo(root.get(columnName), lt) },
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}