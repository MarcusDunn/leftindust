package com.leftindust.mockingbird.icd

import com.leftindust.mockingbird.graphql.types.search.ListFilter
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.metamodel.SetAttribute

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