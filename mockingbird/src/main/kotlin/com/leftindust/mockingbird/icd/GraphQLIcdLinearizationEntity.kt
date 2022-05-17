package com.leftindust.mockingbird.icd

data class GraphQLIcdLinearizationEntity(
    val context: String?,
    override val id: String?,
    val languageTitle: GraphQLIcdLanguageSpecificText?,
    val definition: GraphQLIcdLanguageSpecificText?,
    val longDefinition: GraphQLIcdLanguageSpecificText?,
    val fullySpecifiedName: GraphQLIcdLanguageSpecificText?,
    val source: String?,
    override val code: String?,
    val codingNote: GraphQLIcdLanguageSpecificText?,
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
) : GraphQLIcdReallySimpleEntity {
    constructor(icdLinearizationEntity: IcdLinearizationEntity) : this(
        context = icdLinearizationEntity.`@context`,
        id = icdLinearizationEntity.`@id`,
        languageTitle = icdLinearizationEntity.title?.let { GraphQLIcdLanguageSpecificText(it) },
        definition = icdLinearizationEntity.definition?.let { GraphQLIcdLanguageSpecificText(it) },
        longDefinition = icdLinearizationEntity.longDefinition?.let { GraphQLIcdLanguageSpecificText(it) },
        fullySpecifiedName = icdLinearizationEntity.fullySpecifiedName?.let { GraphQLIcdLanguageSpecificText(it) },
        source = icdLinearizationEntity.source,
        code = icdLinearizationEntity.code,
        codingNote = icdLinearizationEntity.codingNote?.let { GraphQLIcdLanguageSpecificText(it) },
        blockId = icdLinearizationEntity.blockId,
        codeRange = icdLinearizationEntity.codeRange,
        classKind = icdLinearizationEntity.classKind,
        child = icdLinearizationEntity.child,
        parent = icdLinearizationEntity.parent,
        foundationChildElsewhere = icdLinearizationEntity.foundationChildElsewhere,
        indexTerm = icdLinearizationEntity.indexTerm,
        inclusion = icdLinearizationEntity.inclusion,
        exclusion = icdLinearizationEntity.exclusion,
        postcoordinationScale = icdLinearizationEntity.postcoordinationScale,
        browserUrl = icdLinearizationEntity.browserUrl,
    )

    override val title: String?
        get() = languageTitle?.value
    override val description: String?
        get() = longDefinition?.value
}