package com.leftindust.mockingbird.graphql.types.input

import com.leftindust.mockingbird.address.AddressType
import com.leftindust.mockingbird.graphql.AbstractGraphQLDto
import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.ignore
import com.leftindust.mockingbird.graphql.types.update
import com.leftindust.mockingbird.patient.Update
import com.leftindust.mockingbird.patient.UpdatePatientDto
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class DeletableMapDelegate<in G, out T : Any>(private val map: Map<String, Any?>, private val clazz: KClass<T>) : ReadOnlyProperty<G, Deletable<T>> {
    companion object {
        inline operator fun <reified T : Any, G> invoke(map: Map<String, Any?>): DeletableMapDelegate<G, T> {
            return DeletableMapDelegate(map, T::class)
        }
    }

    override fun getValue(thisRef: G, property: KProperty<*>): Deletable<T> {
        return if (map.containsKey(property.name)) {
            map[property.name]?.let {p ->
                if (p is Map<*, *>) {
                    clazz.members.map{e -> (e.returnType.classifier as KClass<*>).java.isEnum}
                    val bruh = clazz.java.declaredFields.map { e -> e.type.isMockingbirdEnum() }
                    // *p.map { it.value }.toTypedArray())
                    Deletable.Update(clazz.constructors.first().call(*p.map { it.value }.toTypedArray()))
                } else {
                    // if this throws there is likely a mismatch between the schema and the class
                    Deletable.Update(p as T)
                }
            } ?: Deletable.Delete()
        } else {
            Deletable.Ignore()
        }
    }
}

fun Class<*>.isMockingbirdEnum() : Boolean {
    return this.isInstance(AddressType::class.java)
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