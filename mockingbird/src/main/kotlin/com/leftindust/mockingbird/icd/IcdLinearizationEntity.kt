package com.leftindust.mockingbird.icd

data class IcdLinearizationEntity(
    val `@context`: String?,
    val `@id`: String?,
    val title: IcdLanguageSpecificText?,
    val definition: IcdLanguageSpecificText?,
    val longDefinition: IcdLanguageSpecificText?,
    val fullySpecifiedName: IcdLanguageSpecificText?,
    val source: String?,
    val code: String?,
    val codingNote: IcdLanguageSpecificText?,
    val blockId: String?,
    val codeRange: String?,
    val classKind: String?,
    val child: List<String>?,
    val parent: List<String>?,
    val foundationChildElsewhere: List<GraphQLIcdTerm>?,
    val indexTerm: List<GraphQLIcdTerm>?,
    val inclusion: List<GraphQLIcdTerm>?,
    val exclusion: List<GraphQLIcdTerm>?,
    val postcoordinationScale: List<GraphQLIcdPostCoordinationScaleInfo?>?,
    val browserUrl: String?,
)