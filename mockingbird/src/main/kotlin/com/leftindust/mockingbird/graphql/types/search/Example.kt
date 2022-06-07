package com.leftindust.mockingbird.graphql.types.search

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

interface Example<T> : CreatePredicate {
    fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<T>): Predicate
}