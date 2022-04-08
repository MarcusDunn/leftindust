package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.external.icd.impl.IcdFoundationEntity

@GraphQLName("IcdFoundationEntity")
data class GraphQLIcdFoundationEntity(
    val context: String?,
    val id: String?,
    val title: GraphQLIcdLanguageSpecificText?,
    val definition: GraphQLIcdLanguageSpecificText?,
    val longDefinition: GraphQLIcdLanguageSpecificText?,
    val fullySpecifiedName: GraphQLIcdLanguageSpecificText?,
    val child: List<String>?,
    val parent: List<String>?,
    val synonym: List<GraphQLIcdTerm>?,
    val narrowerTerm: List<GraphQLIcdTerm>?,
    val inclusion: List<GraphQLIcdTerm>?,
    val exclusion: List<GraphQLIcdTerm>?,
    val browserUrl: String?,
) {
    constructor(foundationEntity: IcdFoundationEntity) : this(
        context = foundationEntity.`@context`,
        id = foundationEntity.`@id`,
        title = foundationEntity.title?.let { GraphQLIcdLanguageSpecificText(it) },
        definition = foundationEntity.definition?.let { GraphQLIcdLanguageSpecificText(it) },
        longDefinition = foundationEntity.longDefinition?.let { GraphQLIcdLanguageSpecificText(it) },
        fullySpecifiedName = foundationEntity.fullySpecifiedName?.let { GraphQLIcdLanguageSpecificText(it) },
        child = foundationEntity.child,
        parent = foundationEntity.parent,
        synonym = foundationEntity.synonym,
        narrowerTerm = foundationEntity.narrowerTerm,
        inclusion = foundationEntity.inclusion,
        exclusion = foundationEntity.exclusion,
        browserUrl = foundationEntity.browserUrl,
    )
}
