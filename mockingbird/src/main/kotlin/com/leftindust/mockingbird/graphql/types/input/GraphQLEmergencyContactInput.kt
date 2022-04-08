package com.leftindust.mockingbird.graphql.types.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.dao.entity.enums.Relationship
import java.lang.IllegalArgumentException

@GraphQLName("EmergencyContactInput")
@GraphQLDescription("either phones or emails MUST contain at least one element")
data class GraphQLEmergencyContactInput(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val relationship: Relationship,
    @GraphQLDescription("defaults to empty list")
    val phones: List<GraphQLPhoneInput>? = null,
    @GraphQLDescription("defaults to empty list")
    val emails: List<GraphQLEmailInput>? = null,
) {
    init {
        if (phones.isNullOrEmpty() && emails.isNullOrEmpty()) {
            throw IllegalArgumentException("either phones or emals MUST contain at least one element in EmergencyContactInput")
        }
    }
}