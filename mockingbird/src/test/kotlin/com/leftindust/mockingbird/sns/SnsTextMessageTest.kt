package com.leftindust.mockingbird.sns

import com.leftindust.mockingbird.MockingbirdApplication
import com.ninjasquad.springmockk.MockkBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
internal class SnsTextMessageTest(
    @Autowired private val snsTextService: SnsTextService

) {
    @MockkBean
    private lateinit var httpSecurity: SecurityWebFilterChain

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun `SNS text works properly`() = runTest {
        val textMessage = "This is a test to show that " +
                "the sns text message functionality works properly"
        val phoneNumber = "+17788821217"

        snsTextService.pubTextSMS(textMessage, phoneNumber)
    }

}