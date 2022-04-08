package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.dao.entity.Event
import com.leftindust.mockingbird.dao.entity.Patient
import com.leftindust.mockingbird.dao.entity.Visit
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateEventRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernatePatientRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent.ID
import com.leftindust.mockingbird.graphql.types.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.GraphQLVisit
import com.leftindust.mockingbird.graphql.types.input.GraphQLEventEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLEventInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLTimeRangeInput
import com.leftindust.mockingbird.util.makeUUID
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class EventDaoImplTest {

    @Nested
    inner class Authorized {
        private val authorizer = mockk<Authorizer> {
            coEvery { getAuthorization(any(), any()) } returns Authorization.Allowed
        }

        @Test
        fun addEvent() {
            val patientRepository = mockk<HibernatePatientRepository> {
                every { findAllById(emptyList()) } returns emptyList()
            }

            val doctorRepository = mockk<HibernateDoctorRepository> {
                every { findAllById(emptyList()) } returns emptyList()
            }

            val savedEvent = mockk<Event>()
            val eventRepository = mockk<HibernateEventRepository> {
                every { save(any()) } returns savedEvent
            }

            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = eventRepository,
                hibernatePatientRepository = patientRepository,
                hibernateDoctorRepository = doctorRepository,
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val graphQLEventInput = mockk<GraphQLEventInput>(relaxed = true)
            val result = eventDaoImpl.addEvent(graphQLEventInput, mockk())
            assertEquals(savedEvent, result)
        }

        @Test
        fun getById() {
            val uuid = makeUUID("hello")
            val event = mockk<Event>()
            val hibernateEventRepository = mockk<HibernateEventRepository> {
                every { getById(uuid) } returns event
            }

            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = hibernateEventRepository,
                hibernatePatientRepository = mockk(),
                hibernateDoctorRepository = mockk(),
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getById(ID(uuid), mockk())

            assertEquals(event, result)
        }

        @Test
        fun getByPatient() {
            val uuid = makeUUID("uuid")
            val patientEvents = mutableSetOf<Event>(mockk())
            val patient = mockk<Patient> {
                every { events } returns patientEvents
            }
            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = mockk(),
                hibernatePatientRepository = mockk {
                    every { getById(uuid) } returns patient
                },
                hibernateDoctorRepository = mockk(),
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getPatientEvents(GraphQLPatient.ID(uuid), mockk())

            assertEquals(patientEvents, result)
        }

        @Test
        fun getByDoctor() {
            val uuid = makeUUID("yeet")
            val doctorEvents = mutableSetOf<Event>(mockk())
            val doctor = mockk<Doctor> {
                every { events } returns doctorEvents
            }
            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = mockk(),
                hibernatePatientRepository = mockk(),
                hibernateDoctorRepository = mockk {
                    every { getById(uuid) } returns doctor
                },
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getByDoctor(GraphQLDoctor.ID(uuid), mockk())

            assertEquals(doctorEvents, result)
        }

        @Test
        fun getByVisit() {
            val uuid = makeUUID()
            val visitEvent = mockk<Event>()
            val mockk = mockk<Visit> {
                every { event } returns visitEvent
            }
            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = mockk(),
                hibernatePatientRepository = mockk(),
                hibernateDoctorRepository = mockk(),
                hibernateVisitRepository = mockk {
                    every { getById(uuid) } returns mockk
                },
                authorizer = authorizer
            )

            val result = eventDaoImpl.getEventVisit(GraphQLVisit.ID(uuid), mockk())

            assertEquals(visitEvent, result)
        }

        @Test
        fun editEvent() {
            val editInput = mockk<GraphQLEventEditInput>(relaxed = true)
            val event = mockk<Event>(relaxed = true) {
                every { reoccurrence } returns null
                every { update(editInput, emptySet(), emptySet()) } just runs
            }
            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = mockk {
                    every { getById(editInput.eid.id) } returns event
                },
                hibernatePatientRepository = mockk {
                    every { findAllById(emptyList()) } returns emptyList()
                },
                hibernateDoctorRepository = mockk {
                    every { findAllById(emptyList()) } returns emptyList()
                },
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.editEvent(editInput, mockk())

            assertEquals(event, result)

            verify { event.update(editInput, emptySet(), emptySet()) }
        }

        @Test
        fun getBetween() {
            val rangeInput = mockk<GraphQLTimeRangeInput>(relaxed = true)
            val events = listOf<Event>(mockk())
            val eventDaoImpl = EventDaoImpl(
                hibernateEventRepository = mockk {
                    every {
                        getAllInRangeOrReoccurrenceIsNotNull(
                            rangeInput.start.toTimestamp(),
                            rangeInput.end.toTimestamp()
                        )
                    } returns events
                },
                hibernatePatientRepository = mockk(),
                hibernateDoctorRepository = mockk(),
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getBetween(rangeInput, mockk())

            assertEquals(events, result)
        }
    }
}