package com.leftindust.mockingbird

import org.springframework.core.convert.converter.Converter

@Deprecated("Use a top level or extension function with the signature `T.() -> Result4k<S, ConversionError<T, S>>`. See CreateCompleteSurveyDto.toCreateCompleteSurvey for an example")
interface FallibleConverter<T, S : Any> : Converter<T, S> {
    override fun convert(source: T): S?
}