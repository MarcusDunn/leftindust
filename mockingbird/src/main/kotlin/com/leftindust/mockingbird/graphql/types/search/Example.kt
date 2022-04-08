package com.leftindust.mockingbird.graphql.types.search

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@GraphQLIgnore
interface Example<T> : PredicateInput {
    @GraphQLIgnore
    fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<T>): Predicate
}