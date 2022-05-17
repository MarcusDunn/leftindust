package com.leftindust.mockingbird

import org.springframework.core.convert.converter.Converter

interface InfallibleConverter<T, S: Any> : Converter<T, S> {
    override fun convert(source: T): S
}

interface FallibleConverter<T, S : Any> : Converter<T, S> {
    override fun convert(source: T): S?
}