package com.leftindust.mockingbird.icd

data class IcdFoundationEntity(
    val `@context`: String?,
    val `@id`: String?,
    val title: IcdLanguageSpecificText?,
    val definition: IcdLanguageSpecificText?,
    val longDefinition: IcdLanguageSpecificText?,
    val fullySpecifiedName: IcdLanguageSpecificText?,
    val child: List<String>?,
    val parent: List<String>?,
    val synonym: List<GraphQLIcdTerm>?,
    val narrowerTerm: List<GraphQLIcdTerm>?,
    val inclusion: List<GraphQLIcdTerm>?,
    val exclusion: List<GraphQLIcdTerm>?,
    val browserUrl: String?,
)

