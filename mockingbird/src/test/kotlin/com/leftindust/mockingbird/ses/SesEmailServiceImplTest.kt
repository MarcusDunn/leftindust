package com.leftindust.mockingbird.ses

import com.leftindust.mockingbird.util.EmailMother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.mail.javamail.JavaMailSender
import javax.mail.Session
import javax.mail.internet.MimeMessage

@OptIn(ExperimentalCoroutinesApi::class)
internal class SesEmailServiceImplTest {

    @Test
    fun sendEmailTest() = runTest {
        val mailSender = mockk<JavaMailSender>(relaxed = true){
            every { createMimeMessage() } returns MimeMessage(mockk<Session>(relaxed = true))
        }
        val sesEmailService: SesEmailService = SesEmailServiceImpl(mailSender)

        sesEmailService.sendHtmlEmail("Test","", listOf(EmailMother.DansEmail.domain.address))
        verify{
            mailSender.send(match<MimeMessage> {
                it.subject == "Test" && it.allRecipients.first()
                    .toString() == EmailMother.DansEmail.domain.address.value
            })
        }
    }
}