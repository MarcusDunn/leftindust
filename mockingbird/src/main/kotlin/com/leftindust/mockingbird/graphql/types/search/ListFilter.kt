package com.leftindust.mockingbird.graphql.types.search

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.From
import javax.persistence.criteria.Predicate
import javax.persistence.metamodel.SetAttribute

interface ListFilter<G> : CreatePredicate {
    fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SetAttribute<X, G>,
    ): Predicate
}