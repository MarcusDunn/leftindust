package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "server.firebase.service")
data class FirebaseConfiguration @ConstructorBinding constructor(
    @DefaultValue("serviceAccountKey.json")
    val accountKeyPath: String
)