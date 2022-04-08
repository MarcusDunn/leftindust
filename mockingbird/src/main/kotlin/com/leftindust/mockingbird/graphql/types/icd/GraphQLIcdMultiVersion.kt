package com.leftindust.mockingbird.graphql.types.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.external.icd.impl.IcdMultiVersion

@GraphQLName("IcdMultiVersion")
data class GraphQLIcdMultiVersion(
    val context: String?,
    val id: String?,
    val title: GraphQLIcdLanguageSpecificText,
    private val latestRelease: String?,
    private val release: List<String>
) {

    constructor(icdMultiVersion: IcdMultiVersion) : this(
        context = icdMultiVersion.`@context`,
        id = icdMultiVersion.`@id`,
        title = GraphQLIcdLanguageSpecificText(icdMultiVersion.title),
        latestRelease = icdMultiVersion.latestRelease,
        release = icdMultiVersion.release,
    )
}