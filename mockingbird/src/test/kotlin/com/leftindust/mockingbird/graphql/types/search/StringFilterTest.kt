package com.leftindust.mockingbird.graphql.types.search

import com.leftindust.mockingbird.dao.entity.NameInfo
import com.leftindust.mockingbird.graphql.types.search.filter.CaseAgnosticStringFilter
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Path
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import javax.persistence.metamodel.SingularAttribute

internal class StringFilterTest {

    @Test
    fun toPredicate() {
        val stringFiler = CaseAgnosticStringFilter(eq = "whoa", strict = true)
        val mockkRoot = mockk<Root<NameInfo>>()
        val mockkCriteriaBuilder = mockk<CriteriaBuilder>()
        val mockkColumnName = mockk<SingularAttribute<NameInfo, String>>()
        val mockkPath = mockk<Path<String>>()
        val mockkPredicate = mockk<Predicate>()
        val mockkCombinedPredicate = mockk<Predicate>()

        every { mockkRoot.get(mockkColumnName) } returns mockkPath
        every { mockkCriteriaBuilder.equal(mockkPath, stringFiler.eq!!.uppercase()) } returns mockkPredicate
        every { mockkCriteriaBuilder.and(mockkPredicate) } returns mockkCombinedPredicate
        every { mockkCriteriaBuilder.upper(any()) } returns mockkPath

        val result = stringFiler.toPredicate(mockkCriteriaBuilder, mockkRoot, mockkColumnName)

        assertEquals(mockkCombinedPredicate, result)
    }
}