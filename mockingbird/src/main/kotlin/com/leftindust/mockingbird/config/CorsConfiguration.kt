package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.context.properties.bind.DefaultValue


@ConfigurationProperties(prefix = "server.cors")
data class CorsConfiguration @ConstructorBinding constructor(
    @DefaultValue("Content-Type, Access-Control-Allow-Origin, Referer, User-Agent")
    val allowedHeaders: String,
    @DefaultValue("POST, OPTIONS")
    val allowedMethods: String,
    @DefaultValue("*")
    val allowedOrigin: String,
    @DefaultValue("3600")
    val maxAge: String,
)