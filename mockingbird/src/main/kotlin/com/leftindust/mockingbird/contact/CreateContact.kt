package com.leftindust.mockingbird.contact

import com.leftindust.mockingbird.email.CreateEmail
import com.leftindust.mockingbird.person.CreateNameInfo
import com.leftindust.mockingbird.person.Relationship
import com.leftindust.mockingbird.phone.CreatePhone


interface CreateContact {
    val nameInfo: CreateNameInfo
    val relationship: Relationship
    val phones: List<CreatePhone>
    val emails: List<CreateEmail>
}

