package com.leftindust.mockingbird.icd

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