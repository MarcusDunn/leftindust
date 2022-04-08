package com.leftindust.mockingbird.dao.clinic

import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.dao.GuardedDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.Action

interface DeleteClinicDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.DELETE to Tables.Clinic))
    }

    override fun necessaryPermissions() = UpdateClinicDao.necessaryPermissions
}