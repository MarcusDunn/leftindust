package com.leftindust.mockingbird.graphql.types.search.example

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.*
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilter
import com.leftindust.mockingbird.graphql.types.search.filter.DateFilter
import com.leftindust.mockingbird.graphql.types.search.filter.IcdListFilter
import com.leftindust.mockingbird.graphql.types.search.filter.WhiteSpaceAgnosticStringFilter
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@GraphQLName("PatientExample")
data class GraphQLPatientExample(
    val firstName: CaseAgnosticStringFilter? = null,
    val lastName: CaseAgnosticStringFilter? = null,
    val dateOfBirth: DateFilter? = null,
    val insuranceNumber: WhiteSpaceAgnosticStringFilter? = null,
    val icdCodes: IcdListFilter? = null,
    override val strict: Boolean,
) : Example<Patient> {
    override fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<Patient>): Predicate {
        val patientNameInfoJoin = root.join(Patient_.nameInfo)
        val predicates = listOfNotNull(
            firstName?.toPredicate(criteriaBuilder, patientNameInfoJoin, NameInfo_.firstName),
            lastName?.toPredicate(criteriaBuilder, patientNameInfoJoin, NameInfo_.lastName),
            dateOfBirth?.toPredicate(criteriaBuilder, root, Patient_.dateOfBirth),
            insuranceNumber?.toPredicate(criteriaBuilder, root, Patient_.insuranceNumber),
            icdCodes?.toPredicate(criteriaBuilder, root.join(Patient_.events).join(Event_.visit), Visit_.icds)
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}

