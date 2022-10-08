package com.leftindust.mockingbird.email

import com.leftindust.mockingbird.validate.EmailAddress

interface CreateEmail {
    val type: EmailType
    val email: EmailAddress
}


