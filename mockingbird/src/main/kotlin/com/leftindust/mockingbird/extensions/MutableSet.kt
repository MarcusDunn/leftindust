package com.leftindust.mockingbird.extensions

/**
 * completely replaces all elements in the set with the elements from the old set
 */
fun <T> MutableSet<T>.replaceAll(set: Set<T>) {
    this.clear()
    this.addAll(set)
}

/**
 * completely replaces all elements in the set with the elements from the old set provided the new set is not null
 */
fun <T> MutableSet<T>.replaceAllIfNotNull(set: Set<T>?) = set?.let { this.replaceAll(set) }
