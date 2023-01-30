package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.search.Filter
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import java.time.LocalDate
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.metamodel.SingularAttribute

data class DateFilterDto(
    val before: LocalDate? = null,
    val after: LocalDate? = null,
    override val strict: Boolean,
) : Filter<LocalDate> {

    override fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, LocalDate>,
    ): Predicate {
        val toTypedArray = listOfNotNull(
            before?.let { criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), before) },
            after?.let { criteriaBuilder.lessThanOrEqualTo(root.get(columnName), after) },
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *toTypedArray)
    }
}