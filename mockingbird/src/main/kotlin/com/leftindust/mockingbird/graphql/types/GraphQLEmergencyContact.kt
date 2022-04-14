package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.email.ReadEmailDao
import com.leftindust.mockingbird.dao.entity.EmergencyContact
import com.leftindust.mockingbird.dao.entity.enums.Relationship
import com.leftindust.mockingbird.dao.phone.ReadPhoneDao
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLName("EmergencyContact")
data class GraphQLEmergencyContact(
    val ecid: ID,
    override val firstName: String,
    override val middleName: String?,
    override val lastName: String,
    val relationship: Relationship,
    @GraphQLDescription(GraphQLPerson.thumbnailDescription)
    override val thumbnail: String? = null,
) : GraphQLPerson {
    @GraphQLName("EmergencyContactId")
    data class ID(val id: UUID)

    constructor(emergencyContact: EmergencyContact) : this(
        ecid = ID(emergencyContact.id!!),
        firstName = emergencyContact.nameInfo.firstName,
        middleName = emergencyContact.nameInfo.middleName,
        lastName = emergencyContact.nameInfo.lastName,
        relationship = emergencyContact.relationship,
    )

    override suspend fun phones(
        @GraphQLIgnore @Autowired phoneDao: ReadPhoneDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPhone> = withContext(Dispatchers.IO) {
        phoneDao.getEmergencyContactPhones(ecid, dataFetchingEnvironment.authToken)
    }.map { GraphQLPhone(it) }

    override suspend fun emails(
        @GraphQLIgnore @Autowired emailDao: ReadEmailDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLEmail> = withContext(Dispatchers.IO) {
        emailDao.getEmergencyContactEmails(ecid, dataFetchingEnvironment.authToken)
    }.map { GraphQLEmail(it) }
}