package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.combineWithStrict
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilterDto
import com.leftindust.mockingbird.graphql.types.search.filter.DateFilterDto
import com.leftindust.mockingbird.graphql.types.search.filter.WhiteSpaceAgnosticStringFilterDto
import com.leftindust.mockingbird.person.NameInfo_
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class PatientExampleDto(
    val firstName: CaseAgnosticStringFilterDto? = null,
    val lastName: CaseAgnosticStringFilterDto? = null,
    val dateOfBirth: DateFilterDto? = null,
    val insuranceNumber: WhiteSpaceAgnosticStringFilterDto? = null,
    override val strict: Boolean,
) : Example<Patient> {
    override fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<Patient>): Predicate {
        val patientNameInfoJoin = root.join(Patient_.nameInfo)
        val predicates = listOfNotNull(
            firstName?.toPredicate(criteriaBuilder, patientNameInfoJoin, NameInfo_.firstName),
            lastName?.toPredicate(criteriaBuilder, patientNameInfoJoin, NameInfo_.lastName),
            dateOfBirth?.toPredicate(criteriaBuilder, root, Patient_.dateOfBirth),
            insuranceNumber?.toPredicate(criteriaBuilder, root, Patient_.insuranceNumber),
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}

