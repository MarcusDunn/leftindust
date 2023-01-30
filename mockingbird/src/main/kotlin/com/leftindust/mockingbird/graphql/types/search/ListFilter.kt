package com.leftindust.mockingbird.graphql.types.search

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.metamodel.SetAttribute

interface ListFilter<G> : CreatePredicate {
    fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SetAttribute<X, G>,
    ): Predicate
}