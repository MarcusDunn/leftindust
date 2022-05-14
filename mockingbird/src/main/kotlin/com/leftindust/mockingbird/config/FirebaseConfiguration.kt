package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "server.firebase.service")
data class FirebaseConfiguration(
    val accountKeyPath: String = "serviceAccountKey.json"
)