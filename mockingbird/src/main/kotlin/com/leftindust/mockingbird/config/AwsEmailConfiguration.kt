package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue

@ConfigurationProperties(prefix = "server.aws.email")
data class AwsEmailConfiguration @ConstructorBinding constructor(
    @DefaultValue("hello@leftindust.com")
    val from: String,
)