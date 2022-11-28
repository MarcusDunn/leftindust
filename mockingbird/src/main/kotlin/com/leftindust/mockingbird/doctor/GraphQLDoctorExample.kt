package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilterDto
import com.leftindust.mockingbird.person.NameInfoEntity_
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

data class GraphQLDoctorExample(
    val firstName: CaseAgnosticStringFilterDto? = null,
    val lastName: CaseAgnosticStringFilterDto? = null,
    override val strict: Boolean,
) : Example<DoctorEntity> {
    override fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<DoctorEntity>): Predicate {
        val doctorNameInfo = root.join(DoctorEntity_.nameInfoEntity)
        val predicates = listOfNotNull(
            firstName?.toPredicate(criteriaBuilder, doctorNameInfo, NameInfoEntity_.firstName),
            lastName?.toPredicate(criteriaBuilder, doctorNameInfo, NameInfoEntity_.lastName),
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}