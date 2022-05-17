package com.leftindust.mockingbird.extensions

fun doThenNull(f: () -> Unit): Nothing? {
    f()
    return null
}