package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "server.aws")
data class AwsConfiguration (
    val defaultFromEmailAddress: String = "hello@leftindust.com"
)