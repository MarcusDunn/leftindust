package com.leftindust.mockingbird.graphql.types

sealed class Deletable<out T> {
    class Delete<T> : Deletable<T>()

    override fun toString(): String = when (this) {
        is Delete -> "Delete"
        is Updatable -> this.toString()
    }
}

sealed class Updatable<out T> : Deletable<T>() {
    class Ignore<T> : Updatable<T>()
    data class Update<T>(val value: T) : Updatable<T>()

    override fun toString() = when (this) {
        is Ignore -> "Ignore"
        is Update -> "Update to $value"
    }
}


fun <T> update(value: T) = Updatable.Update(value)
fun <T> delete(@Suppress("UNUSED_PARAMETER") valueType: Class<T>? = null) = Deletable.Delete<T>()
fun <T> ignore(@Suppress("UNUSED_PARAMETER") valueType: Class<T>? = null) = Updatable.Ignore<T>()
