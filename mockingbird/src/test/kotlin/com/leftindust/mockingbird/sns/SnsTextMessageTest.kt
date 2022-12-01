package com.leftindust.mockingbird.sns

import com.amazonaws.services.sns.AmazonSNS
import com.leftindust.mockingbird.validate.PhoneNumber
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
internal class SnsTextMessageTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    internal fun `Test SNS sends an SMS message`() = runTest {
        val snsClient = mockk<AmazonSNS>(relaxed = true) {
            coEvery { publish(any()) } returns mockk()
        }
        val snsTextService = SnsTextServiceImpl(snsClient)

        snsTextService.publishSMS(PhoneNumber.of("16042225555"), "test message")
        verify(exactly = 1) { snsClient.publish(any()) }
    }
}