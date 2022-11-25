package com.leftindust.mockingbird.thymeleaf

import com.leftindust.mockingbird.MockingbirdApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.approvaltests.Approvals
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(SpringExtension::class)
@Import(value = [SpringTemplateEngine::class])
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
