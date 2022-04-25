package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.extensions.Authorization
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * a firebase based implementation of Authorizer
 * @property authorizationDao handles all interaction with the user permissions stored in the DB
 */
@Service
internal class AuthorizerImpl(
    @Autowired private val authorizationDao: AuthorizationDao
) : Authorizer {
    private val logger = LogManager.getLogger()

    override fun getAuthorization(action: Action, user: MediqToken): Authorization {
        val uid = user.uid ?: return Authorization.Denied.also { logger.info("$user does not have a uid") }
        logger.info("user $user has uid $uid")
        return if (authorizationDao.isAdmin(uid)) {
            Authorization.Allowed
        } else if (authorizationDao.isPatient(uid)) { // todo don't do this.
            Authorization.Allowed
        } else {
            val roles = getRoles(user) ?: return Authorization.Denied
            roles
                .map { it.action }
                .any { it.isSuperset(action) }
                .toAuthorization()
        }.also { logger.info("user $user's attempt to $action was $it") }
    }

    private fun getRoles(user: MediqToken): List<AccessControlList>? =
        user.uid?.let { authorizationDao.getRolesForUserByUid(it) }
            .also { logger.info("$user has roles $it") }

    private fun Boolean.toAuthorization() = if (this) Authorization.Allowed else Authorization.Denied
}
