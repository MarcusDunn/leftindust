package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.user.MediqUser

object UserMother {
    private const val `marcus's first name` = "Marcus"
    private const val `marcus's last name` = "Dunn"
    private const val `marcus's middle name` = "Elliot Schulz"
    val marcusUserAccount = MediqUser(
        uniqueId = "-JiGh_31GA20JabpZBfa",
        group = null,
        nameInfo = NameInfo(
            firstName = `marcus's first name`,
            lastName = `marcus's last name`,
            middleName = `marcus's middle name`
        )
    )
}