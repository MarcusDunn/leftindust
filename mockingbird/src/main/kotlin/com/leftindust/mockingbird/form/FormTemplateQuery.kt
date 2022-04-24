package com.leftindust.mockingbird.form

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.exceptions.GraphQLKotlinException
import com.expediagroup.graphql.server.operations.Query
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class FormTemplateQuery(
    private val formDao: ReadFormDao
) : Query {

    @GraphQLDescription("fetch survey templates by one getting a range or the survey id")
    @Deprecated("removed due to lack of query verification", replaceWith = ReplaceWith("surveysByRange"))
    suspend fun surveys(
        range: GraphQLRangeInput? = null,
        surveys: List<GraphQLFormTemplate.ID>? = null,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLFormTemplate> = when {
        range == null && surveys != null -> withContext(Dispatchers.IO) {
            surveysById(surveys, dataFetchingEnvironment)
        }
        range != null && surveys == null -> withContext(Dispatchers.IO) {
            surveysByRange(range, dataFetchingEnvironment)
        }
        else -> throw GraphQLKotlinException("invalid argument combination to surveys")
    }

    private fun surveysByRange(
        range: GraphQLRangeInput,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = formDao.getMany(range, dataFetchingEnvironment.authToken).map(::GraphQLFormTemplate)

    private fun surveysById(
        surveys: List<GraphQLFormTemplate.ID>,
        dataFetchingEnvironment: DataFetchingEnvironment
    ) = formDao.getByIds(surveys, dataFetchingEnvironment.authToken).map(::GraphQLFormTemplate)
}