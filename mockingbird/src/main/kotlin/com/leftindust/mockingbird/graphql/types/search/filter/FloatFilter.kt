package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.search.Filter
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.metamodel.SingularAttribute

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