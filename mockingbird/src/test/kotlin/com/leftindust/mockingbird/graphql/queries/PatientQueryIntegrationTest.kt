package com.leftindust.mockingbird.graphql.queries

import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.impl.repository.HibernatePatientRepository
import com.leftindust.mockingbird.util.EntityStore
import com.leftindust.mockingbird.util.debugPrint
import com.leftindust.mockingbird.util.gqlRequest
import com.leftindust.mockingbird.util.integration.NoAuthIntegrationTest
import com.leftindust.mockingbird.util.integration.Resource.DATABASE
import com.leftindust.mockingbird.util.verifyOnlyDataExists
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.parallel.ResourceAccessMode.READ_WRITE
import org.junit.jupiter.api.parallel.ResourceLock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient

@AutoConfigureWebTestClient
@ResourceLock(DATABASE, mode = READ_WRITE)
class PatientQueryIntegrationTest(
    @Autowired private val webTestClient: WebTestClient
) : NoAuthIntegrationTest() {
    companion object {
        lateinit var patient: Patient

        @AfterAll
        @JvmStatic
        fun cleanup(
            @Autowired patientRepository: HibernatePatientRepository,
        ) {
            patientRepository.deleteAll()
        }

        @BeforeAll
        @JvmStatic
        fun setup(
            @Autowired patientRepository: HibernatePatientRepository,
        ) {
            patient = patientRepository.save(EntityStore.patient("PatientQueryIntegrationTest.setup"))
        }
    }


    @Test
    internal fun `get patients by range`() {
        webTestClient.gqlRequest(
            // language=graphql
            """
            query {
                patientsByRange(range: {from: 0, to: 10}) {
                    firstName
                }
            }
        """.trimIndent()
        )
            .verifyOnlyDataExists("patientsByRange")
            .jsonPath("data.patientsByRange[*].firstName")
            .exists()
    }

    @Test
    internal fun `get patients by id`() {
        webTestClient.gqlRequest(
            // language=graphql
            """
            query {
                patientsByPid(pids: [{id: "${patient.id}"}]) {
                    pid { id }
                }
            }
        """.trimIndent()
        )
            .verifyOnlyDataExists("patientsByPid")
            .jsonPath("data.patientsByPid[*].pid.id")
            .isEqualTo(patient.id.toString())
    }

    @Test
    internal fun `get patients by visit`() {
        webTestClient.gqlRequest(
            // language=graphql
            """
            query {
                patientsByPid(pids: [{id: "${patient.id}"}]) {
                    pid { id }
                }
            }
        """.trimIndent()
        )
            .verifyOnlyDataExists("patientsByPid")
            .jsonPath("data.patientsByPid[*].pid.id")
            .isEqualTo(patient.id.toString())
    }

    @Test
    internal fun `get patient emails`() {
        webTestClient.gqlRequest(
            // language=graphql
            """
            query {
                patientsByPid(pids: [{id: "${patient.id}"}]) {
                    emails {
                        email
                        type
                    }
                }
            }
        """.trimIndent()
        )
            .debugPrint()
            .verifyOnlyDataExists("patientsByPid")
            .jsonPath("data.patientsByPid[*].emails[*].email")
            .isEqualTo(patient.email.first().email)
    }
}