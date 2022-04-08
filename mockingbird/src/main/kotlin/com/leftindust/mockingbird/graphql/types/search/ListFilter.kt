package com.leftindust.mockingbird.graphql.types.search

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.From
import javax.persistence.criteria.Predicate
import javax.persistence.metamodel.SetAttribute

@GraphQLIgnore
interface ListFilter<G> : PredicateInput {
    @GraphQLIgnore
    fun <Z, X> toPredicate(
        criteriaBuilder: CriteriaBuilder,
        root: From<Z, X>,
        columnName: SetAttribute<X, G>
    ): Predicate
}