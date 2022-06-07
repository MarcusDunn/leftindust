package com.leftindust.mockingbird.doctor

import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilter
import com.leftindust.mockingbird.person.NameInfo_
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

data class GraphQLDoctorExample(
    val firstName: CaseAgnosticStringFilter? = null,
    val lastName: CaseAgnosticStringFilter? = null,
    override val strict: Boolean,
) : Example<Doctor> {
    override fun toPredicate(criteriaBuilder: CriteriaBuilder, root: Root<Doctor>): Predicate {
        val doctorNameInfo = root.join(Doctor_.nameInfo)
        val predicates = listOfNotNull(
            firstName?.toPredicate(criteriaBuilder, doctorNameInfo, NameInfo_.firstName),
            lastName?.toPredicate(criteriaBuilder, doctorNameInfo, NameInfo_.lastName),
        ).toTypedArray()
        return combineWithStrict(criteriaBuilder, *predicates)
    }
}