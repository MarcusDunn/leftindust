package com.leftindust.mockingbird

import dev.forkhandles.result4k.Failure
import kotlin.reflect.KClass

class ConversionError<T : Any, S : Any>(
    private val source: T,
    private val target: KClass<S>,
    private val cause: Exception,
) {
    companion object {
        inline fun <T : Any, reified S : Any> Failure(source: T, cause: Exception): Failure<ConversionError<T, S>> {
            return Failure(ConversionError(source, S::class, cause))
        }

        inline fun <T : Any, reified S : Any> T.ConversionFailure(cause: Exception): Failure<ConversionError<T, S>> {
            return Failure(this, cause)
        }

        inline fun <T : Any, reified S : Any> Failure(
            source: T,
            cause: ConversionError<*, *>,
        ): Failure<ConversionError<T, S>> {
            return Failure(ConversionError(source, S::class, cause.toException()))
        }

        inline fun <T : Any, reified S : Any> T.ConversionFailure(cause: ConversionError<*, *>): Failure<ConversionError<T, S>> {
            return Failure(this, cause.toException())
        }
    }

    fun toException(): Exception {
        return Exception("Could not convert $source to $target", cause)
    }
}