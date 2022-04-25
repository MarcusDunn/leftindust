package com.leftindust.mockingbird.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

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
