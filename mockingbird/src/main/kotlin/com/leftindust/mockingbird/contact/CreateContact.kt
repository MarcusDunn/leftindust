package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone
import com.leftindust.mockingbird.phone.CreatePhoneDto
import java.util.UUID

interface CreateContact {
    val nameInfo: CreateNameInfo
    val relationship: Relationship
    val phones: List<CreatePhone>
    val emails: List<CreateEmail>
}

interface CreateContactPatient : CreateContact {
    val patientId: UUID
    override val nameInfo: CreateNameInfo
    override val relationship: Relationship
    override val phones: List<CreatePhone>
    override val emails: List<CreateEmail>
}