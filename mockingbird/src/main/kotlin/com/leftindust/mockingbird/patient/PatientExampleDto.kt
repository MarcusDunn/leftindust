package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilterDto
import com.leftindust.mockingbird.graphql.types.search.filter.DateFilterDto
import com.leftindust.mockingbird.graphql.types.search.filter.WhiteSpaceAgnosticStringFilterDto
import com.leftindust.mockingbird.person.NameInfoEntity_
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

data class PatientExampleDto(
    val firstName: CaseAgnosticStringFilterDto? = null,
    val lastName: CaseAgnosticStringFilterDto? = null,
    val dateOfBirth: DateFilterDto? = null,
    val insuranceNumber: WhiteSpaceAgnosticStringFilterDto? = null,
    override val strict: Boolean,
) : Example<PatientEntity> {
    override fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<PatientEntity>): Predicate {
        val patientNameInfoJoin = root.join(PatientEntity_.nameInfoEntity)
        val predicates = listOfNotNull(
            firstName?.toPredicate(criteriaBuilder, patientNameInfoJoin, NameInfoEntity_.firstName),
            lastName?.toPredicate(criteriaBuilder, patientNameInfoJoin, NameInfoEntity_.lastName),
            dateOfBirth?.toPredicate(criteriaBuilder, root, PatientEntity_.dateOfBirth),
            insuranceNumber?.toPredicate(criteriaBuilder, root, PatientEntity_.insuranceNumber)
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}

