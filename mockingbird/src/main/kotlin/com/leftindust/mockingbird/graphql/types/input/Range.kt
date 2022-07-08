package com.leftindust.mockingbird.graphql.types.input

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface Range {
    val from: Int
    val to: Int
    val size: Int
        get() = to - from
    fun toPageable(sort: Sort): Pageable = OffsetBasedPageRequest(size, from, sort)
}

private data class OffsetBasedPageRequest(private val limit: Int, private val offset: Int, private val sort: Sort) : Pageable {

    init {
        require(limit >= 1) { "Limit must not be less than one!" }
        require(offset >= 0) { "Offset index must not be less than zero!" }
    }

    override fun getPageNumber(): Int {
        return offset / limit
    }

    override fun getPageSize(): Int {
        return limit
    }

    override fun getOffset(): Long {
        return offset.toLong()
    }

    override fun getSort(): Sort {
        return sort
    }

    override fun next(): Pageable {
        // Typecast possible because number of entries cannot be bigger than integer (primary key is integer)
        return OffsetBasedPageRequest(pageSize, (getOffset() + pageSize).toInt(), sort)
    }

    private fun previous(): Pageable {
        // The integers are positive. Subtracting does not let them become bigger than integer.
        return if (hasPrevious()) OffsetBasedPageRequest(pageSize, (getOffset() - pageSize).toInt(), sort) else this
    }

    override fun previousOrFirst(): Pageable {
        return if (hasPrevious()) previous() else first()
    }

    override fun first(): Pageable {
        return OffsetBasedPageRequest(pageSize, 0, sort)
    }

    override fun withPage(pageNumber: Int): Pageable {
        return OffsetBasedPageRequest(limit, offset + (limit * pageNumber), sort)
    }

    override fun hasPrevious(): Boolean {
        return offset > limit
    }
}