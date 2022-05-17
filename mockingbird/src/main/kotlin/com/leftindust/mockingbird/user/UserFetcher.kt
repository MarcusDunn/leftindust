package com.leftindust.mockingbird.user

import com.google.firebase.auth.UserRecord
import org.springframework.stereotype.Component

@Component
class UserFetcher {

    fun getUserInfo(uid: String): UserRecord {
        TODO()
    }
}