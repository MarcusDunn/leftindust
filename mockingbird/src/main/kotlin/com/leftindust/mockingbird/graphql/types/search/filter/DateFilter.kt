package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.input.GraphQLDateInput
import com.leftindust.mockingbird.graphql.types.search.Filter
import java.sql.Date
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.From
import javax.persistence.criteria.Predicate
import javax.persistence.metamodel.SingularAttribute

data class DateFilter(
    val before: GraphQLDateInput? = null,
    val after: GraphQLDateInput? = null,
    override val strict: Boolean,
) : Filter<Date> {

    override fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SingularAttribute<X, Date>
    ): Predicate {
        val toTypedArray = listOfNotNull(
            before?.let { criteriaBuilder.greaterThanOrEqualTo(root.get(columnName), before.toDate()) },
            after?.let { criteriaBuilder.lessThanOrEqualTo(root.get(columnName), after.toDate()) },
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *toTypedArray)
    }
}