package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue


@ConfigurationProperties(prefix = "icd.client")
data class IcdApiClientConfiguration @ConstructorBinding constructor(
    @DefaultValue("localhost:80/icd")
    val url: String,
)