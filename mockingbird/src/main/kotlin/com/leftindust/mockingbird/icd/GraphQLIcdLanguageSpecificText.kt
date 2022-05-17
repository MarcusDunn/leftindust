package com.leftindust.mockingbird.icd

data class GraphQLIcdLanguageSpecificText(
    val language: String?,
    val value: String?,
) {
    constructor(languageSpecificText: IcdLanguageSpecificText) : this(
        language = languageSpecificText.`@language`,
        value = languageSpecificText.`@value`,
    )
}
