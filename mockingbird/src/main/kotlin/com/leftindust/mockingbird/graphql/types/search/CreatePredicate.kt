package com.leftindust.mockingbird.graphql.types.search

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate

interface CreatePredicate {
    val strict: Boolean
}
fun CreatePredicate.combineWithStrict(criteriaBuilder: CriteriaBuilder, vararg predicates: Predicate): Predicate {
    return if (strict) {
        criteriaBuilder.and(*predicates)
    } else {
        criteriaBuilder.or(*predicates)
    }
}