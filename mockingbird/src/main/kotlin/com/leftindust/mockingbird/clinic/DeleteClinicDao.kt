package com.leftindust.mockingbird.clinic

import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.persistance.GuardedDao
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.auth.Action

interface DeleteClinicDao : GuardedDao {
    companion object {
        val necessaryPermissions = setOf(Action(Crud.DELETE to Tables.Clinic))
    }

    override fun necessaryPermissions() = UpdateClinicDao.necessaryPermissions
}