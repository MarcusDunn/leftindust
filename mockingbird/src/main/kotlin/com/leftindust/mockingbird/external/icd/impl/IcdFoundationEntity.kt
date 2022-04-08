package com.leftindust.mockingbird.external.icd.impl

import com.leftindust.mockingbird.graphql.types.icd.GraphQLIcdPostCoordinationScaleInfo
import com.leftindust.mockingbird.graphql.types.icd.GraphQLIcdTerm

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

class IcdLanguageSpecificText(
    val `@language`: String?,
    val `@value`: String?,
)

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

data class IcdMultiVersion(
    val `@context`: String?,
    val `@id`: String?,
    val title: IcdLanguageSpecificText,
    val latestRelease: String?,
    val release: List<String>
)
