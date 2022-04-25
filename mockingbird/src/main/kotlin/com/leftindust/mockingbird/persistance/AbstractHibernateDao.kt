package com.leftindust.mockingbird.persistance

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.extensions.isAllowed

abstract class AbstractHibernateDao(private val authorizer: Authorizer) {

    infix fun MediqToken.can(action: Action): Boolean {
        return authorizer.getAuthorization(action, this).isAllowed()
    }

    infix fun MediqToken.can(action: Pair<Crud, Tables>): Boolean {
        return authorizer.getAuthorization(Action(action), this).isAllowed()
    }

    infix fun MediqToken.can(actions: List<Pair<Crud, Tables>>): Boolean {
        return actions.all { authorizer.getAuthorization(Action(it), this).isAllowed() }
    }
}