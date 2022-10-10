package com.leftindust.mockingbird

import org.springframework.core.convert.converter.Converter

@Deprecated("Use a top level or extension function with the signature T.() -> S. See Address.toAddressDtoConverter for an example.")
interface InfallibleConverter<T, S : Any> : Converter<T, S> {
    override fun convert(source: T): S
}

