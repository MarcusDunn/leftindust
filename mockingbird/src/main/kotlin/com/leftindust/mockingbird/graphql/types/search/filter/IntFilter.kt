package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.search.Filter
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.metamodel.SingularAttribute

data class IntFilter(
    val gt: Int? = null,
    val lt: Int? = null,
    val eq: Int? = null,
    val ne: Int? = null,
    override val strict: Boolean
) : Filter<Int> {
    override fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, Int>
    ): Predicate {
        val predicates = listOfNotNull(
            gt?.let { criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), gt) },
            lt?.let { criteriaBuilder.lessThanOrEqualTo(root.get(columnName), lt) },
            eq?.let { criteriaBuilder.equal(root.get(columnName), eq) },
            ne?.let { criteriaBuilder.notEqual(root.get(columnName), ne) },
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}