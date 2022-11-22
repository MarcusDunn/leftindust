package com.leftindust.mockingbird.email_service

import com.leftindust.mockingbird.MockingbirdApplication
import com.leftindust.mockingbird.util.EmailMother
import com.leftindust.mockingbird.validate.EmailAddress
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.ContextConfiguration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templateresolver.StringTemplateResolver
import java.util.*
import javax.mail.Session
import javax.mail.internet.MimeMessage

@OptIn(ExperimentalCoroutinesApi::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [MockingbirdApplication::class])
internal class EmailSenderServiceImplTest {

    @Autowired
    private lateinit var templateEngine: SpringTemplateEngine

    @Test
    fun sendEmailTest() = runTest {
        val mailSender = mockk<JavaMailSender>(relaxed = true){
            every { createMimeMessage() } returns MimeMessage(mockk<Session>(relaxed = true))
        }
        val emailSenderService: EmailSenderService = EmailSenderServiceImpl(mailSender)
        val context = Context(Locale.ENGLISH, mapOf("hello" to "bow hello"))
        val html = templateEngine.process("TemplateSample", context).toString()

        emailSenderService.sendHtmlEmail("Test", html, listOf(EmailAddress.of("borisv.ca@gmail.com")))
        verify{
            mailSender.send(match<MimeMessage> {
                it.subject == "Test"})
        }
    }
}