package com.leftindust.mockingbird.user

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.google.firebase.auth.UserRecord

@GraphQLName("FirebaseInfo")
data class GraphQLFirebaseInfo(
    val uid: String? = null,
    val tenantId: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val tokensValidAfterTimestamp: Long? = null,
) {
    constructor(userRecord: UserRecord) : this(
        uid = userRecord.uid,
        tenantId = userRecord.tenantId,
        email = userRecord.email,
        phoneNumber = userRecord.phoneNumber,
        displayName = userRecord.displayName,
        photoUrl = userRecord.photoUrl,
        tokensValidAfterTimestamp = userRecord.tokensValidAfterTimestamp,
    )
}
