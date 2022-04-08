package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.dao.entity.Action

/**
 * Handles all Authorization for requests
 */
interface Authorizer {
    /**
     * checks to see if a certain action is permitted for a user
     * @param action an object specifying the type of permissions the user is asking for
     * @param user the token the user has sent
     * @returns either an Allowed or Denied
     */
    fun getAuthorization(action: Action, user: MediqToken): Authorization
}
