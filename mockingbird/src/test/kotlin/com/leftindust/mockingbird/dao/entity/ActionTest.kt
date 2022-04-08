package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.dao.Tables
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.sql.Timestamp

internal class ActionTest {

    @Test
    fun `check action with more detail are subsets of more general action`() {
        val superAction = Action(Crud.READ to Tables.Patient)
        val subAction = Action(
            referencedTableName = Tables.Patient,
            permissionType = Crud.READ,
            startTime = Timestamp.valueOf("2018-09-01 09:01:15")
        )
        assertEquals(true, superAction isSuperset subAction)
    }

    @Test
    fun `equal actions are supersets of each other`() {
        val superAction = Action(Crud.READ to Tables.Patient)
        val subAction = Action(
            referencedTableName = Tables.Patient,
            permissionType = Crud.READ,
            startTime = Timestamp.valueOf("2018-09-01 09:01:15")
        )
        assertEquals(true, superAction isSuperset subAction)
    }
}