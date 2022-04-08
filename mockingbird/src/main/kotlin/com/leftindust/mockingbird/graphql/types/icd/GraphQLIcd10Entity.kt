package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("Icd10Entity")
data class GraphQLIcd10Entity(
    val context: String?,
    val id: String?,
    val title: GraphQLIcdLanguageSpecificText,
    val definition: GraphQLIcdLanguageSpecificText,
    val longDefinition: GraphQLIcdLanguageSpecificText,
    val fullySpecifiedName: GraphQLIcdLanguageSpecificText,
    val source: String,
    val code: String,
    val note: GraphQLIcdLanguageSpecificText,
    val codingHint: GraphQLIcdLanguageSpecificText,
    val classKind: String,
    val child: List<String>,
    val parent: String,
    val indexTerm: List<GraphQLIcdTerm>,
    val inclusion: List<GraphQLIcdTerm>,
    val exclusion: List<GraphQLIcdTerm>,
    val browserUrl: String,
)
