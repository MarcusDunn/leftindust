package com.leftindust.mockingbird.persistance

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.UUID
import org.hibernate.annotations.UuidGenerator

interface JpaEntity // marker interface for all entities

@MappedSuperclass
abstract class AbstractJpaPersistable : JpaEntity {
    companion object

    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (other !is AbstractJpaPersistable) return false

        return if (null == this.id) false else this.id == other.id
    }

    override fun hashCode(): Int {
        return 13
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"
}