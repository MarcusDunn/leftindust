package com.leftindust.mockingbird

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

sealed class LogMessage(private val message: String) {
    override fun toString() = message
}

class ReturningNullMessage(cause: String) : LogMessage("Returning null. Cause: $cause")

class SummaryMessage<T: LogMessage>(private val summaryTitle: String, private val messages: List<T>) : LogMessage("\n$summaryTitle:\n${messages.joinToString { "\n\t" }}") {
    operator fun plus(message: T): SummaryMessage<T> = SummaryMessage(summaryTitle, messages + messages)
}

sealed class UpdatedEntityMessage(targetEntity: AbstractJpaPersistable, message: String) : LogMessage("Updated $targetEntity. $message")

class UpdatedEntityFieldMessage<T>(targetEntity: AbstractJpaPersistable, fieldName: KProperty<T>, updatedValue: T) : UpdatedEntityMessage(targetEntity, "$fieldName to $updatedValue")

class DeletedEntityFieldMessage<T>(targetEntity: AbstractJpaPersistable, fieldName: KProperty<T>) : UpdatedEntityMessage(targetEntity, "Deleted $fieldName")

class DidNotUpdateEntityFieldMessage<T>(targetEntity: AbstractJpaPersistable, fieldName: KProperty<T>) : UpdatedEntityMessage(targetEntity, "Did not change $fieldName")

sealed class MockingbirdException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class NullEntityIdInConverterException(entity: AbstractJpaPersistable) : MockingbirdException("Tried to convert but $entity has a null id")

class NullSubQueryException(origin: AbstractGraphQLDto<*>, functionName: KCallable<*>) : MockingbirdException("${functionName.name} returned null when called used to resolve a sub query of $origin.")