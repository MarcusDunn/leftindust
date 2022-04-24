package com.leftindust.mockingbird.persistance

import com.leftindust.mockingbird.auth.Action

interface GuardedDao {
    fun necessaryPermissions(): Set<Action>
}