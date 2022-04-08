package com.leftindust.mockingbird.external.icd

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "icd.client")
data class IcdApiClient(
    val url: String = "localhost:80/icd",
)