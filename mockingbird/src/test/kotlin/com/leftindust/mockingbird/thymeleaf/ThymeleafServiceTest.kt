package com.leftindust.mockingbird.thymeleaf

import com.leftindust.mockingbird.config.ThymeleafTestConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.approvaltests.Approvals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(SpringExtension::class)
@Import(ThymeleafTestConfiguration::class)
@ActiveProfiles("test")
internal class ThymeleafServiceTest {

    @Autowired
    private lateinit var templateEngine: SpringTemplateEngine

    @org.junit.jupiter.api.Test
    fun thymeleafTemplateTest() = runTest {
        val context = Context(Locale.ENGLISH, mapOf("hello" to "bow hello"))
        val html = templateEngine.process("TemplateSample", context).toString()
        Approvals.verify(html)
    }
}
