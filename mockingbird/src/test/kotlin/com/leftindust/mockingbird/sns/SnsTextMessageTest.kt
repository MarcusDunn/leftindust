package com.leftindust.mockingbird.sns

import com.ninjasquad.springmockk.MockkBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.web.server.SecurityWebFilterChain

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
internal class SnsTextMessageTest(@Autowired private val snsTextService: SnsTextService) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun `SNS text works properly`() = runTest {
        val textMessage = "Test from leftindust"
        val phoneNumber = ""

        if(phoneNumber.isNotEmpty()) snsTextService.publishSMS(phoneNumber, textMessage)
    }
}