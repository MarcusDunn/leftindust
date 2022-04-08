package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.entity.FormField
import com.leftindust.mockingbird.dao.form.feild.ReadFormFieldDao
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("FormField")
data class GraphQlFormField(
    val ffid: ID,
    val title: String,
    val dataType: DataType,
    val number: Int,
    val intUpperBound: Int?,
    val intLowerBound: Int?,
    val floatUpperBound: Float?,
    val floatLowerBound: Float?,
    val dateUpperBound: GraphQLDate?,
    val dateLowerBound: GraphQLDate?,
    val textRegex: String?,
    val jsonMetaData: String?,
    private val authContext: GraphQLAuthContext,
) {
    @GraphQLName("FormFieldId")
    data class ID(val id: UUID)

    constructor(formField: FormField, graphQLAuthContext: GraphQLAuthContext) : this(
        ffid = ID(formField.id!!),
        title = formField.title,
        number = formField.number,
        dataType = formField.dataType,
        intUpperBound = formField.intUpperBound,
        intLowerBound = formField.intLowerBound,
        floatUpperBound = formField.floatUpperBound,
        floatLowerBound = formField.floatLowerBound,
        dateUpperBound = formField.dateUpperBound?.toLocalDate()?.let { GraphQLDate(it) },
        dateLowerBound = formField.dateLowerBound?.toLocalDate()?.let { GraphQLDate(it) },
        textRegex = formField.textRegex,
        jsonMetaData = formField.jsonMetaData,
        authContext = graphQLAuthContext,
    )

    @GraphQLDescription("The multiselect possibilities, is null unless the form dataType is SingleMuliSelect or MultiMuliSelect")
    suspend fun multiSelectPossibilities(@GraphQLIgnore @Autowired readFormFieldDao: ReadFormFieldDao): List<String>? =
        if (dataType == DataType.SingleMultiSelect || dataType == DataType.MultiMultiSelect)
            withContext(Dispatchers.IO) {
                readFormFieldDao.getFormFieldMultiSelectPossibilities(ffid, authContext.mediqAuthToken)
            }
        else {
            null
        }
}