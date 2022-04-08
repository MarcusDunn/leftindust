package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.external.icd.impl.IcdLanguageSpecificText

@GraphQLName("IcdLanguageSpecificText")
data class GraphQLIcdLanguageSpecificText(
    val language: String?,
    val value: String?,
) {
    constructor(languageSpecificText: IcdLanguageSpecificText) : this(
        language = languageSpecificText.`@language`,
        value = languageSpecificText.`@value`,
    )
}
