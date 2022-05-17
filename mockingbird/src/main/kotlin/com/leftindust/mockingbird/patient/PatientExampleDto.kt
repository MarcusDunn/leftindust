package com.leftindust.mockingbird.patient

import com.leftindust.mockingbird.event.Event_
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilter
import com.leftindust.mockingbird.graphql.types.search.filter.DateFilter
import com.leftindust.mockingbird.graphql.types.search.filter.WhiteSpaceAgnosticStringFilter
import com.leftindust.mockingbird.icd.IcdListFilter
import com.leftindust.mockingbird.person.NameInfo_
import com.leftindust.mockingbird.visit.Visit_
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class PatientExampleDto(
    val firstName: CaseAgnosticStringFilter? = null,
    val lastName: CaseAgnosticStringFilter? = null,
    val dateOfBirth: DateFilter? = null,
    val insuranceNumber: WhiteSpaceAgnosticStringFilter? = null,
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

