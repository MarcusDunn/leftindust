package com.leftindust.mockingbird.icd

import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("IcdSimpleEntity")
data class GraphQLIcdSimpleEntity(
    override val id: String,
    private val internalId: String?,
    private val internalTitle: String?,
    val stemId: String?,
    val isLeaf: Boolean,
    val postcoordinationAvailability: GraphQLIcdPostcoordinationAvailability,
    val hasCodingNote: Boolean,
    val hasMaternalChapterLink: Boolean,
    val matchingPVs: List<GraphQLIcdSimplePropertyValue>,
    val propertiesTruncated: Boolean,
    val isResidualOther: Boolean,
    val isResidualUnspecified: Boolean,
    val chapter: String?,
    val theCode: String?,
    val score: Double,
    val titleIsASearchResult: Boolean,
    val titleIsTopScore: Boolean,
    val entityType: GraphQLIcdEntityType,
    val important: Boolean,
    val descendants: List<GraphQLIcdSimpleEntity>,
    override val title: String?,
) : GraphQLIcdReallySimpleEntity {
    suspend fun entity(@Autowired @GraphQLIgnore icdFetcher: IcdFetcher): GraphQLIcdFoundationEntity? {
        return icdFetcher.getDetails(GraphQLFoundationIcdCode(internalId ?: return null))
    }

    fun urlId(asUrl: Boolean? = false): String? = if (asUrl == true) {
        internalId
    } else {
        internalId?.let { GraphQLFoundationIcdCode(it).code }
    }

    fun tagTitle(withTags: Boolean? = true): String? = if (withTags != false) {
        internalTitle
    } else {
        internalTitle?.replace(Regex("<[^>]*>"), "")
    }

    override val code: String?
        get() = theCode
    override val description: String?
        get() = null
}