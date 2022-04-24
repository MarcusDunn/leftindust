package com.leftindust.mockingbird.form

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.authToken
import graphql.schema.DataFetchingEnvironment
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

data class GraphQLFormTemplate(
    val tid: ID,
    val name: String,
) {
    @GraphQLName("FormTemplateId")
    data class ID(val id: UUID)

    constructor(form: Form) : this(
        tid = ID(form.id!!),
        name = form.name,
    )

    @GraphQLDescription("The sections of the form sorted by number")
    suspend fun sections(
        @GraphQLIgnore @Autowired formSectionDao: ReadFormSectionDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLFormSection> = withContext(Dispatchers.IO) {
        formSectionDao.getByTemplate(tid, dataFetchingEnvironment.authToken)
    }.sortedBy { it.number }
        .map { GraphQLFormSection(it) }
}

