package com.leftindust.mockingbird.graphql.types

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.ContextualDeserializer

@JsonDeserialize(using = Deletable.DeleteableDeserializer::class)
sealed class Deletable<out T> {
    class Delete<T> : Deletable<T>()

    class DeleteableDeserializer(
        private val valueType: JavaType? = null,
    ) : JsonDeserializer<Deletable<*>>(), ContextualDeserializer {
        override fun createContextual(ctxt: DeserializationContext, property: BeanProperty): JsonDeserializer<*> =
            DeleteableDeserializer(property.type.containedType(0))

        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Deletable<*> =
            update<Any>(ctxt.readValue(p, valueType))

        override fun getNullValue(ctxt: DeserializationContext): Deletable<*> =
            if (ctxt.parser.currentToken == JsonToken.VALUE_NULL)
                delete(valueType!!.rawClass)
            else
                ignore(valueType!!.rawClass)
    }

    override fun toString(): String = when (this) {
        is Delete -> "Delete"
        is Updatable -> this.toString()
    }
}

@JsonDeserialize(using = Updatable.UpdatableDeserializer::class)
sealed class Updatable<out T> : Deletable<T>() {
    class Ignore<T> : Updatable<T>()
    data class Update<T>(val value: T) : Updatable<T>()

    override fun toString() = when (this) {
        is Ignore -> "Ignore"
        is Update -> "Update to $value"
    }

    class UpdatableDeserializer(
        private val valueType: JavaType? = null,
    ) : JsonDeserializer<Updatable<*>>(), ContextualDeserializer {
        override fun createContextual(ctxt: DeserializationContext, property: BeanProperty): JsonDeserializer<*> =
            UpdatableDeserializer(property.type.containedType(0))

        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Updatable<*> =
            update<Any>(ctxt.readValue(p, valueType))

        override fun getNullValue(ctxt: DeserializationContext): Updatable<*> = ignore(valueType!!.rawClass)
    }
}


private fun <T> update(value: T) = Updatable.Update(value)
private fun <T> delete(@Suppress("UNUSED_PARAMETER") valueType: Class<T>? = null) = Deletable.Delete<T>()
private fun <T> ignore(@Suppress("UNUSED_PARAMETER") valueType: Class<T>? = null) = Updatable.Ignore<T>()
