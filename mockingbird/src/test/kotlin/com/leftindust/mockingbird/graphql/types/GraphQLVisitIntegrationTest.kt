package com.leftindust.mockingbird.graphql.types

import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.dao.entity.Visit
import com.leftindust.mockingbird.dao.impl.repository.HibernateEventRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateVisitRepository
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
internal class GraphQLVisitIntegrationTest(
    @Autowired private val webTestClient: WebTestClient
) : NoAuthIntegrationTest() {
    companion object {
        lateinit var patient: Patient
        lateinit var visit: Visit
        lateinit var event: Event

        @AfterAll
        @JvmStatic
        fun cleanup(
            @Autowired patientRepository: HibernatePatientRepository,
            @Autowired visitRepository: HibernateVisitRepository,
            @Autowired eventRepository: HibernateEventRepository,
        ) {
            patientRepository.deleteAll()
            visitRepository.deleteAll()
            eventRepository.deleteAll()
        }

        @BeforeAll
        @JvmStatic
        fun setup(
            @Autowired patientRepository: HibernatePatientRepository,
            @Autowired visitRepository: HibernateVisitRepository,
            @Autowired eventRepository: HibernateEventRepository,
        ) {
            patient = EntityStore.patient("PatientQueryIntegrationTest.setup")
            event = EntityStore.event("PatientQueryIntegrationTest.setup")
            visit = Visit(event, "PatientQueryIntegrationTest.setup")

            event = eventRepository.save(event)
            visit = visitRepository.save(visit)
            patient = patientRepository.save(patient)

            event.visit = visit
            patient.addEvent(event)

            visit = visitRepository.save(visit)
            event = eventRepository.save(event)
            patient = patientRepository.save(patient)
        }
    }

    @Test
    internal fun `test get patient by visit`() {
        webTestClient.gqlRequest(
            //language=graphql
            """
            { 
                visits(vids: [{id: "${visit.id}"}]) {
                    patients {
                        firstName                
                    }
                }
            }
        """.trimIndent()
        )
            .debugPrint()
            .verifyOnlyDataExists("visits")
            .jsonPath("data.visits[0].patients[0].firstName")
            .isEqualTo(patient.nameInfo.firstName)
    }
}