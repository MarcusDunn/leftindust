package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.entity.FormSection
import com.leftindust.mockingbird.dao.form.feild.ReadFormFieldDao
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("FormSection")
data class GraphQLFormSection(
    val fsid: ID,
    val name: String,
    val number: Int,
    @GraphQLDescription("Max 50 000 chars")
    val description: String?,
    private val authContext: GraphQLAuthContext,
) {
    @GraphQLName("FormSectionId")
    data class ID(val id: UUID)

    constructor(section: FormSection, graphQLAuthContext: GraphQLAuthContext) : this(
        fsid = ID(section.id!!),
        name = section.name,
        number = section.number,
        description = section.description,
        authContext = graphQLAuthContext,
    )

    @GraphQLDescription("Note that I do not provide a stable order to these fields")
    suspend fun fields(
        @GraphQLIgnore @Autowired formFieldDao: ReadFormFieldDao
    ): List<GraphQlFormField> = withContext(Dispatchers.IO) {
        formFieldDao.getSectionFields(fsid, authContext.mediqAuthToken)
    }.map { GraphQlFormField(it, authContext) }
}