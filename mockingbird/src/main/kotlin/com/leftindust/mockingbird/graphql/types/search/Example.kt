package com.leftindust.mockingbird.graphql.types.search

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

interface Example<T> : CreatePredicate {
    fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<T>): Predicate
}