package com.leftindust.mockingbird

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import dev.forkhandles.result4k.Failure
import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.util.UUID
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import org.springframework.core.convert.converter.Converter

sealed class LogMessage(private val message: String) {
    override fun toString() = message
}

class CreatedEntityMessage<T : AbstractJpaPersistable>(entity: T) : LogMessage("Created $entity")

class FailedConversionMessage<G : Any, T : Any>(val source: T, target: KClass<G>) :
    LogMessage("Returning null. Failed to convert $source to $target") {
    companion object {
        @Suppress("unused") // the receiver helps with type inference on G
        inline fun <T : Any, reified G : Any> Converter<T, G>.FailedConversionMessage(source: T) =
            FailedConversionMessage(source, G::class)
    }
}

sealed class NoUpdatesOccurred<T : AbstractJpaPersistable>(targetEntityClass: KClass<T>, reason: String) :
    LogMessage("No $targetEntityClass was updated. $reason")

class NoUpdatesOccurredNoEntityWithId<T : AbstractJpaPersistable>(targetEntityClass: KClass<T>, uuid: UUID) :
    NoUpdatesOccurred<T>(targetEntityClass, "Nothing was found with id $uuid")

sealed class UpdatedEntityFieldMessage<T>(
    targetEntity: AbstractJpaPersistable,
    property: KProperty<T>,
    message: String
) :
    LogMessage("Updated $targetEntity.${property.name}: $message")

class MissedCollectionAddNoEntityWithId<T>(
    targetEntity: AbstractJpaPersistable,
    property: KProperty<Collection<T>>,
    id: UUID
) :
    UpdatedEntityFieldMessage<Collection<T>>(
        targetEntity,
        property,
        "Did not add an entity to collection. Could not find one with id $id"
    )

class ClearedEntityCollectionMessage<T>(targetEntity: AbstractJpaPersistable, property: KProperty<Collection<T>>) :
    UpdatedEntityFieldMessage<Collection<T>>(targetEntity, property, "Cleared collection")

class AddedAllEntityCollectionMessage<T>(
    targetEntity: AbstractJpaPersistable,
    property: KProperty<Collection<T>>,
    newElements: Collection<T>
) :
    UpdatedEntityFieldMessage<Collection<T>>(targetEntity, property, "Added $newElements")

class RemovedElementMessage<T>(
    targetEntity: AbstractJpaPersistable,
    property: KProperty<Collection<T>>,
    removedElement: T
) :
    UpdatedEntityFieldMessage<Collection<T>>(targetEntity, property, "Removed $removedElement")

class AddedElementMessage<T>(targetEntity: AbstractJpaPersistable, property: KProperty<Collection<T>>, element: T) :
    UpdatedEntityFieldMessage<Collection<T>>(targetEntity, property, "Added $element")

class SetEntityFieldMessage<T>(targetEntity: AbstractJpaPersistable, property: KProperty<T>, updatedValue: T) :
    UpdatedEntityFieldMessage<T>(targetEntity, property, "Set to $updatedValue")

class SetToNullEntityFieldMessage<T>(targetEntity: AbstractJpaPersistable, property: KProperty<T>) :
    UpdatedEntityFieldMessage<T>(targetEntity, property, "Set to null")

class NoOpUpdatedEntityFieldMessage<T>(
    targetEntity: AbstractJpaPersistable,
    property: KProperty<T>,
    reason: String? = null
) :
    UpdatedEntityFieldMessage<T>(targetEntity, property, "No update ${reason?.let { ". Cause: $it" }}")

sealed class MockingbirdException(errorMessage: String, cause: Throwable? = null) : RuntimeException(errorMessage, cause)

class NullEntityIdInConverterException(entity: AbstractJpaPersistable) :
    MockingbirdException("Tried to convert but $entity has a null id")

class NullSubQueryException(origin: AbstractGraphQLDto<*>, functionName: KCallable<*>) :
    MockingbirdException("${functionName.name} returned null when called used to resolve a sub query of $origin.")

class InconvertibleDtoException(dto: Any, kClass: KClass<*>) :
    MockingbirdException("Tried to convert $dto to ${kClass.simpleName} but failed") {
    companion object {
        inline operator fun <reified T> invoke(dto: Any): InconvertibleDtoException =
            InconvertibleDtoException(dto, T::class)
    }
}

class InconvertibleEntityException(entity: AbstractJpaPersistable, kClass: KClass<*>) :
    MockingbirdException("Tried to convert $entity to ${kClass.simpleName} but failed") {
    companion object {
        inline operator fun <reified T> invoke(entity: AbstractJpaPersistable): InconvertibleEntityException =
            InconvertibleEntityException(entity, T::class)
    }
}

sealed interface PersistenceError {
    class FindException(private val entity: KClass<*>, private val id: UUID)
        : PersistenceError, GraphQLError, MockingbirdException("Could not find entity of type ${entity.simpleName} given the id $id")
    {
        companion object {
            operator fun invoke(entity: KClass<*>, id: UUID): Failure<PersistenceError> {
                return Failure(FindException(entity, id))
            }
        }

        override fun <e: GraphQLError> getMessage(): String {
            super<MockingbirdException>.message
        }

        override fun getLocations(): MutableList<SourceLocation>? = null
        override fun getErrorType(): ErrorClassification? = null
    }

    class CreateException(private val entity: KClass<*>, private val cause : String) : PersistenceError {
        companion object {
            operator fun invoke(entity: KClass<*>, cause: String): Failure<PersistenceError> {
                return Failure(CreateException(entity, cause))
            }
        }

        private val message = "Could not create entity of type ${entity.simpleName}, cause: $cause"
        override fun getMessage() = message

        override fun getLocations(): MutableList<SourceLocation>? = null
        override fun getErrorType(): ErrorClassification? = null
        override fun toException(): MockingbirdException {
            return MockingbirdException(message)
        }
    }
}