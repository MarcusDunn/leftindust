package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.dao.AuthorizationDao
import com.leftindust.mockingbird.dao.entity.AccessControlList
import com.leftindust.mockingbird.graphql.types.GraphQLPermissions
import com.leftindust.mockingbird.util.unit.MockDataFetchingEnvironment
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PermissionsQueryTest {
    private val authorizer = mockk<AuthorizationDao>()

    @Test
    fun permissions() {
        val actionID = UUID.randomUUID()

        val acl = mockk<AccessControlList>(relaxed = true) {
            every { action.id } returns actionID
        }

        every { authorizer.getRolesForUserByUid("uid") } returns listOf(acl)
        val permissionsQuery = PermissionsQuery(authorizer)
        val result = runBlocking { permissionsQuery.permissions("uid", MockDataFetchingEnvironment.withVerifiedMediqToken) }
        val permission = GraphQLPermissions(listOf(acl))
        assertEquals(permission, result)
    }
}