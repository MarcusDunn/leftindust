package com.leftindust.mockingbird.graphql.types.input

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface Range {
    val from: Int
    val to: Int
}

private val DEFAULT_SORT = Sort.unsorted()
fun Range.toPageable(sort: Sort = DEFAULT_SORT): Pageable = OffsetLimitPageable(from, to, sort = sort)
private class OffsetLimitPageable(private val offset: Int, private val size: Int, private val pageNumber: Int = 0, private val sort: Sort = DEFAULT_SORT) : Pageable {
    init {
        require(offset >= 0) { "Offset must not be less than zero!" }
        require(pageNumber >= 0) { "Page must not be less than zero!" }
        require(size >= 1) { "Page size must not be less than one!" }
    }

    override fun getPageNumber() = pageNumber
    override fun getPageSize() = size
    override fun getOffset(): Long = (offset + pageNumber * pageSize).toLong()
    override fun getSort(): Sort = sort
    override fun next() = OffsetLimitPageable(offset, pageSize, pageNumber + 1, sort)
    override fun previousOrFirst() = if (hasPrevious()) OffsetLimitPageable(offset, pageSize, pageNumber - 1) else first()
    override fun first() = OffsetLimitPageable(offset, pageSize, 0, sort)
    override fun withPage(pageNumber: Int) = OffsetLimitPageable(offset, size, pageNumber, sort)
    override fun hasPrevious(): Boolean = pageNumber > 0
}