package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.ignore
import com.leftindust.mockingbird.graphql.types.update
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class DeletableMapDelegate<in G, out T : Any>(private val map: Map<String, Any?>, private val clazz: KClass<T>) : ReadOnlyProperty<G, Deletable<T>> {
    companion object {
        inline operator fun <reified T : Any, G> invoke(map: Map<String, Any?>): DeletableMapDelegate<G, T> {
            return DeletableMapDelegate(map, T::class)
        }
    }

    override fun getValue(thisRef: G, property: KProperty<*>): Deletable<T> {
        return if (map.containsKey(property.name)) {
            map[property.name]?.let {value ->
                if (value is Map<*, *>) {
                    Deletable.Update(clazz.constructors.first().call(value))
                }
                else {
                    // if this throws there is likely a mismatch between the schema and the class
                    Deletable.Update(value as T)
                }
            } ?: Deletable.Delete()
        } else {
            Deletable.Ignore()
        }
    }
}


class UpdatableMapDelegate<in G, out T : Any>(map: Map<String, Any?>, clazz: KClass<T>) : ReadOnlyProperty<G, Updatable<T>> {
    private val deletableMapDeletable = DeletableMapDelegate<G, T>(map, clazz)

    companion object {
        inline operator fun <reified T : Any, G> invoke(map: Map<String, Any?>): UpdatableMapDelegate<G, T> {
            return UpdatableMapDelegate(map, T::class)
        }
    }

    override operator fun getValue(thisRef: G, property: KProperty<*>): Updatable<T> = when (val value = deletableMapDeletable.getValue(thisRef, property)) {
        is Deletable.Delete -> ignore()
        is Deletable.Ignore -> ignore()
        is Deletable.Update -> update(value.value)
    }
}

fun <T : Any> toMap(obj: T): Map<String, Any?> {
    return (obj::class as KClass<T>).memberProperties.associate { prop ->
        prop.name to prop.get(obj)?.let { value ->
            if (value::class.isData) {
                toMap(value)
            } else {
                value
            }
        }
    }
}