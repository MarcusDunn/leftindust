package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.dao.entity.Action

interface GuardedDao {
    fun necessaryPermissions(): Set<Action>
}