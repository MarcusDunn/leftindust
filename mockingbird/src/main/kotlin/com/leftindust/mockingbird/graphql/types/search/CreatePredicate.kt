package com.leftindust.mockingbird.graphql.types.search

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

interface CreatePredicate {
    val strict: Boolean

    fun combineWithStrict(criteriaBuilder: CriteriaBuilder, vararg predicates: Predicate): Predicate {
        return if (strict) {
            criteriaBuilder.and(*predicates)
        } else {
            criteriaBuilder.or(*predicates)
        }
    }
}