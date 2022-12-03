package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.AddedAllEntityCollectionMessage
import com.leftindust.mockingbird.ClearedEntityCollectionMessage
import com.leftindust.mockingbird.MissedCollectionAddNoEntityWithId
import com.leftindust.mockingbird.NoOpUpdatedEntityFieldMessage
import com.leftindust.mockingbird.SetEntityFieldMessage
import com.leftindust.mockingbird.SetToNullEntityFieldMessage
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.persistance.JpaEntity
import java.util.UUID
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0
import mu.KLogger
import org.springframework.graphql.data.ArgumentValue

sealed class Deletable<out T : Any> {
    class Delete<T : Any> : Deletable<T>()
    class Ignore<T : Any> : Deletable<T>()
    data class Update<T : Any>(val value: T) : Deletable<T>()

    override fun toString(): String = when (this) {
        is Delete -> "Delete"
        is Ignore -> "Ignore"
        is Update -> "Update to $value"
    }

    inline fun <G : Any> map(transform: (T) -> G): Deletable<G> {
        return when (this) {
            is Delete -> delete()
            is Ignore -> Ignore()
            is Update -> Update(transform(this.value))
        }
    }
}

fun <T : Any> Deletable<T>.applyDeletable(entity: AbstractJpaPersistable, setter: KMutableProperty0<T?>, logger: KLogger) {
    when (this) {
        is Deletable.Delete -> {
            logger.trace { SetToNullEntityFieldMessage(entity, setter) }
            setter.set(null)
        }
        is Deletable.Ignore -> {
            logger.trace { NoOpUpdatedEntityFieldMessage(entity, setter) }
        }
        is Deletable.Update -> {
            logger.trace { SetEntityFieldMessage(entity, setter, this.value) }
            setter.set(this.value)
        }
    }
}

fun <T : Any> Updatable<T>.applyUpdatable(entity: AbstractJpaPersistable, setter: KMutableProperty0<T>, logger: KLogger) {
    when (this) {
        is Updatable.Ignore -> {
            logger.trace { NoOpUpdatedEntityFieldMessage(entity, setter) }
        }
        is Updatable.Update -> {
            logger.trace { SetEntityFieldMessage(entity, setter, this.value) }
            setter.set(this.value)
        }
    }
}

suspend fun <G : JpaEntity, T: AbstractGraphQLDto.GraphQLID<UUID>> Updatable<List<T>>.applyUpdatableGqlId(
    entity: AbstractJpaPersistable,
    property: KProperty0<MutableSet<G>>,
    addToCollection: suspend (T) -> Unit?,
    logger: KLogger,
) {
    when (this) {
        is Updatable.Ignore -> {
            logger.trace { NoOpUpdatedEntityFieldMessage(entity, property) }
        }
        is Updatable.Update -> {
            property.getter.call().clear()
            logger.trace { ClearedEntityCollectionMessage(entity, property) }
            val (values, notFound) = this.value.map { it to addToCollection(it) }.partition { it.second != null }
            notFound.forEach { MissedCollectionAddNoEntityWithId(entity, property, it.first.value) }
            val newElements = values.map { it.second!! }
            AddedAllEntityCollectionMessage(entity, property, newElements)
        }
    }
}

sealed class Updatable<out T : Any> {
    class Ignore<T : Any> : Updatable<T>()
    data class Update<T : Any>(val value: T) : Updatable<T>()

    override fun toString() = when (this) {
        is Ignore -> "Ignore"
        is Update -> "Update to $value"
    }

    inline fun <G : Any> map(transform: (T) -> G): Updatable<G> {
        return when (this) {
            is Ignore -> Ignore()
            is Update -> Update(transform(this.value))
        }
    }
}


fun <T : Any> update(value: T) = Updatable.Update(value)
fun <T : Any> delete(@Suppress("UNUSED_PARAMETER") valueType: Class<T>? = null) = Deletable.Delete<T>()
fun <T : Any> ignore(@Suppress("UNUSED_PARAMETER") valueType: Class<T>? = null) = Updatable.Ignore<T>()

fun <T : Any> ArgumentValue<T>.toUpdatable(): Updatable<T> {
    return if (isOmitted) {
        Updatable.Ignore()
    } else if (isPresent) {
        Updatable.Update(value()!!)
    } else {
        Updatable.Ignore()
    }
}

fun <T : Any> ArgumentValue<T>.toDeletable(): Deletable<T> {
    return if (isOmitted) {
        // omitted - do nothing
        Deletable.Ignore()
    } else if (isPresent) {
        // present - update
        Deletable.Update(value()!!) // !! is safe as spring guarantees that if it is present is it no null
    } else {
        // explicitly set to null - delete
        Deletable.Delete()
    }
}
