package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action

class NotAuthorizedException(requester: MediqToken, vararg actions: Action) :
    Throwable("user $requester cannot $actions") {
    constructor(requester: MediqToken, vararg pairs: Pair<Crud, Tables>) : this(
        requester = requester,
        actions = pairs
            .map { Action(it) }
            .toTypedArray()
    )

    constructor(requester: MediqToken, createFormsAndUpdatePatients: List<Pair<Crud, Tables>>) : this(
        requester,
        *createFormsAndUpdatePatients.toTypedArray()
    )
}

