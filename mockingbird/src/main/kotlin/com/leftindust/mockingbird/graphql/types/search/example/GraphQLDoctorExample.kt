package com.leftindust.mockingbird.graphql.types.search.example

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.dao.entity.Doctor_
import com.leftindust.mockingbird.dao.entity.NameInfo_
import com.leftindust.mockingbird.graphql.types.search.Example
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilter
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@GraphQLName("DoctorExample")
data class GraphQLDoctorExample(
    val firstName: CaseAgnosticStringFilter? = null,
    val lastName: CaseAgnosticStringFilter? = null,
    override val strict: Boolean
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