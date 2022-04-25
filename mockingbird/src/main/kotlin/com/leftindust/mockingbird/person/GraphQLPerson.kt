package com.leftindust.mockingbird.person

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.email.GraphQLEmail
import com.leftindust.mockingbird.email.ReadEmailDao
import com.leftindust.mockingbird.phone.ReadPhoneDao
import com.leftindust.mockingbird.phone.GraphQLPhone
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("Person")
interface GraphQLPerson {
    val firstName: String
    val middleName: String?
    val lastName: String
    suspend fun phones(
        @GraphQLIgnore @Autowired phoneDao: ReadPhoneDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLPhone>

    suspend fun emails(
        @GraphQLIgnore @Autowired emailDao: ReadEmailDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLEmail>

    @GraphQLDescription(thumbnailDescription)
    val thumbnail: String?

    companion object {
        const val thumbnailDescription = "a base64 icon. Cannot be over 10 000 characters when base64 encoded"
    }
}