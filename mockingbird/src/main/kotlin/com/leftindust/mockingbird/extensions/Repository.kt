package com.leftindust.mockingbird.extensions

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.EntityNotFoundException

/**
 * An optimistic findAllById, failing if anyone doesn't match
 * @param ids list of primary keys for [T]
 * @returns the set of all matching ids.
 * @throws EntityNotFoundException if no [T]s match anything in [ids]
 */
inline fun <reified T> JpaRepository<T, UUID>.getByIds(ids: Collection<UUID>): Set<T> {
    val result = findAllById(ids.map { it }).toSet()
    if (result.size != ids.size)
        throw EntityNotFoundException("one or more of the requested ids was not found, expected to find ${ids.size} instead found ${result.size}")
    else
        return result
}