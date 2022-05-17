package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto

interface CreateContact {
    val firstName: String
    val middleName: String?
    val lastName: String
    val relationship: Relationship
    val phones: List<CreatePhone>
    val emails: List<CreateEmail>
}