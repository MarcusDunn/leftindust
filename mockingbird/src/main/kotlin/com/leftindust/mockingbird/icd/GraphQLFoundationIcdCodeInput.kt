package com.leftindust.mockingbird.icd

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("FoundationIcdCodeInput")
data class GraphQLFoundationIcdCodeInput(
    @GraphQLDescription("""full A ICD-11 Foundation Code URL""")
    val url: String
)