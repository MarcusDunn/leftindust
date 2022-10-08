package com.leftindust.mockingbird.person

import java.util.UUID

interface NameInfo {
    val id: UUID?
    val firstName: String
    val lastName: String
    val middleName: String?
}