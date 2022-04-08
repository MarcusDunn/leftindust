package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.AuthorizationDao
import com.leftindust.mockingbird.dao.entity.AccessControlList
import com.leftindust.mockingbird.graphql.types.GraphQLPermissions
import io.mockk.every
import io.mockk.mockk
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PermissionsQueryTest {
    private val authorizer = mockk<AuthorizationDao>()
    private val authContext = mockk<GraphQLAuthContext>()

    @Test
    fun permissions() {
        val actionID = UUID.randomUUID()

        val acl = mockk<AccessControlList>(relaxed = true) {
            every { action.id } returns actionID
        }
        every { authContext.mediqAuthToken } returns mockk {
            every { isVerified() } returns true
        }
        every { authorizer.getRolesForUserByUid("uid") } returns listOf(acl)
        val permissionsQuery = PermissionsQuery(authorizer)
        val result = runBlocking { permissionsQuery.permissions("uid", authContext) }
        val permission = GraphQLPermissions(listOf(acl))
        assertEquals(permission, result)
    }
}