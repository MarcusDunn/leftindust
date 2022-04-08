package com.leftindust.mockingbird.graphql.types.search

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

@GraphQLIgnore
interface PredicateInput {
    val strict: Boolean

    @GraphQLIgnore
    fun combineWithStrict(criteriaBuilder: CriteriaBuilder, vararg predicates: Predicate): Predicate {
        return if (strict) {
            criteriaBuilder.and(*predicates)
        } else {
            criteriaBuilder.or(*predicates)
        }
    }
}