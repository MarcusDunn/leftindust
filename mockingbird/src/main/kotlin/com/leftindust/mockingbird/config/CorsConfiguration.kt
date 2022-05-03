package com.leftindust.mockingbird.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "server.cors")
data class CorsConfiguration(
    val allowedHeaders: String = "Content-Type, Access-Control-Allow-Origin, Referer, User-Agent",
    val allowedMethods: String = "POST, OPTIONS",
    val allowedOrigin: String = "*",
    val maxAge: String = "3600",
)