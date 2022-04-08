package com.leftindust.mockingbird.graphql.types.search.filter

import com.leftindust.mockingbird.graphql.types.icd.GraphQLFoundationIcdCodeInput
import com.leftindust.mockingbird.graphql.types.search.ListFilter
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.From
import javax.persistence.criteria.Predicate
import javax.persistence.metamodel.SetAttribute

class IcdListFilter(
    val includes: List<GraphQLFoundationIcdCodeInput>? = null,
    val notIncludes: List<GraphQLFoundationIcdCodeInput>? = null,
    override val strict: Boolean
) : ListFilter<String> {
    override fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SetAttribute<X, String>
    ): Predicate {
        val predicates = listOfNotNull(
            includes?.map { criteriaBuilder.isMember(it.url, root.get(columnName)) },
            notIncludes?.map { criteriaBuilder.isNotMember(it.url, root.get(columnName)) }
        ).flatten().toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}