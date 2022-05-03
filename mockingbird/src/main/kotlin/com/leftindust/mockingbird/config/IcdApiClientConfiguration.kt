package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "icd.client")
data class IcdApiClientConfiguration(
    val url: String = "localhost:80/icd",
)