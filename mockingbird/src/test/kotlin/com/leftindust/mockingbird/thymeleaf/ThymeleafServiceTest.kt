package com.leftindust.mockingbird.thymeleaf

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.approvaltests.Approvals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ThymeleafServiceTest {

    @Autowired
    private lateinit var templateEngine: SpringTemplateEngine

    @org.junit.jupiter.api.Test
    fun thymeleafTemplateTest() = runTest {
        val context = Context(Locale.ENGLISH, mapOf("hello" to "bow hello"))
        print(context)
        val html = templateEngine.process("TemplateSample", context).toString()
        Approvals.verify(html)
    }
}
