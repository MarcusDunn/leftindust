package com.leftindust.mockingbird

import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import kotlin.reflect.KCallable

open class LogMessage(private val effect: String, private val cause: String) {
    override fun toString() = "$effect. Cause: $cause"
}

class CreatedEntityMessage(entity: AbstractJpaPersistable, cause: String) : LogMessage("Created $entity", cause)

class NullFromServiceMessage(serviceMethod: KCallable<*>, cause: String) : LogMessage("Returning null from ${serviceMethod.name}", cause)

class GraphQlUpdateOnNonExistentEntity(controllerMethod: KCallable<*>, cause: String) : LogMessage("Returning null from ${controllerMethod.name}", cause)

class NullEntityIdInConverterException(entity: AbstractJpaPersistable) : IllegalArgumentException("Tried to convert $entity but has a null id")

class NullSubQueryException(origin: AbstractGraphQLDto<*>, functionName: KCallable<*>) : NullPointerException("${functionName.name} returned null when called used to resolve a sub query of $origin.")