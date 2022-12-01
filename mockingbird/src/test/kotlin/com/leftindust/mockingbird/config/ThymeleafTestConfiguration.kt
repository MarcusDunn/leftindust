package com.leftindust.mockingbird.config

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@TestConfiguration
@ActiveProfiles("test")
@EnableConfigurationProperties(ThymeleafProperties::class)
class ThymeleafTestConfiguration {
    @Bean
    fun templateResolver(thymeleafProperties: ThymeleafProperties): SpringResourceTemplateResolver {
        val templateResolver = SpringResourceTemplateResolver()
        templateResolver.prefix = thymeleafProperties.prefix
        templateResolver.suffix = thymeleafProperties.suffix
        templateResolver.templateMode = TemplateMode.parse(thymeleafProperties.mode)
        return templateResolver
    }

    @Bean
    fun springTemplateEngine(thymeleafProperties: ThymeleafProperties): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver(thymeleafProperties))
        templateEngine.enableSpringELCompiler = thymeleafProperties.isEnableSpringElCompiler
        return templateEngine
    }
}