package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.email.ReadEmailDao
import com.leftindust.mockingbird.dao.entity.EmergencyContact
import com.leftindust.mockingbird.dao.entity.enums.Relationship
import com.leftindust.mockingbird.dao.phone.ReadPhoneDao
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("EmergencyContact")
data class GraphQLEmergencyContact(
    val ecid: ID,
    override val firstName: String,
    override val middleName: String?,
    override val lastName: String,
    val relationship: Relationship,
    private val authContext: GraphQLAuthContext,
    @GraphQLDescription(GraphQLPerson.thumbnailDescription)
    override val thumbnail: String? = null,
) : GraphQLPerson {
    @GraphQLName("EmergencyContactId")
    data class ID(val id: UUID)

    constructor(emergencyContact: EmergencyContact, authContext: GraphQLAuthContext) : this(
        ecid = ID(emergencyContact.id!!),
        firstName = emergencyContact.nameInfo.firstName,
        middleName = emergencyContact.nameInfo.middleName,
        lastName = emergencyContact.nameInfo.lastName,
        relationship = emergencyContact.relationship,
        authContext = authContext,
    )

    override suspend fun phones(
        @GraphQLIgnore @Autowired phoneDao: ReadPhoneDao
    ): List<GraphQLPhone> = withContext(Dispatchers.IO) {
        phoneDao.getEmergencyContactPhones(ecid, authContext.mediqAuthToken)
    }.map { GraphQLPhone(it) }

    override suspend fun emails(
        @GraphQLIgnore @Autowired emailDao: ReadEmailDao
    ): List<GraphQLEmail> = withContext(Dispatchers.IO) {
        emailDao.getEmergencyContactEmails(ecid, authContext.mediqAuthToken)
    }.map { GraphQLEmail(it) }
}