package com.leftindust.mockingbird.dao.entity.superclasses

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractJpaPersistable {

    companion object

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    var id: UUID? = null

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (other !is AbstractJpaPersistable) return false

        return if (null == this.id) false else this.id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"
}