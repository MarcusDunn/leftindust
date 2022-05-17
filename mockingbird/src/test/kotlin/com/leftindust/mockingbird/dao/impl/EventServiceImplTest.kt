package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.visit.Visit
import com.leftindust.mockingbird.doctor.DoctorRepository
import com.leftindust.mockingbird.event.HibernateEventRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.extensions.Authorization
import com.leftindust.mockingbird.doctor.DoctorDto
import com.leftindust.mockingbird.event.EventDto.EventDtoId
import com.leftindust.mockingbird.patient.PatientDto
import com.leftindust.mockingbird.visit.VisitDto
import com.leftindust.mockingbird.event.UpdateEventDto
import com.leftindust.mockingbird.event.CreateEventDto
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

internal class EventServiceImplTest {

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

            val doctorRepository = mockk<DoctorRepository> {
                every { findAllById(emptyList()) } returns emptyList()
            }

            val savedEvent = mockk<Event>()
            val eventRepository = mockk<HibernateEventRepository> {
                every { save(any()) } returns savedEvent
            }

            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = eventRepository,
                hibernatePatientRepository = patientRepository,
                doctorRepository = doctorRepository,
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val createEventDto = mockk<CreateEventDto>(relaxed = true)
            val result = eventDaoImpl.addEvent(createEventDto, mockk())
            assertEquals(savedEvent, result)
        }

        @Test
        fun getById() {
            val uuid = makeUUID("hello")
            val event = mockk<Event>()
            val hibernateEventRepository = mockk<HibernateEventRepository> {
                every { getById(uuid) } returns event
            }

            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = hibernateEventRepository,
                hibernatePatientRepository = mockk(),
                doctorRepository = mockk(),
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getById(EventDtoId(uuid), mockk())

            assertEquals(event, result)
        }

        @Test
        fun getByPatient() {
            val uuid = makeUUID("uuid")
            val patientEvents = mutableSetOf<Event>(mockk())
            val patient = mockk<Patient> {
                every { events } returns patientEvents
            }
            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = mockk(),
                hibernatePatientRepository = mockk {
                    every { getById(uuid) } returns patient
                },
                doctorRepository = mockk(),
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getPatientEvents(PatientDto.PatientDtoId(uuid), mockk())

            assertEquals(patientEvents, result)
        }

        @Test
        fun getByDoctor() {
            val uuid = makeUUID("yeet")
            val doctorEvents = mutableSetOf<Event>(mockk())
            val doctor = mockk<Doctor> {
                every { events } returns doctorEvents
            }
            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = mockk(),
                hibernatePatientRepository = mockk(),
                doctorRepository = mockk {
                    every { getById(uuid) } returns doctor
                },
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getByDoctor(DoctorDto.DoctorDtoId(uuid), mockk())

            assertEquals(doctorEvents, result)
        }

        @Test
        fun getByVisit() {
            val uuid = makeUUID()
            val visitEvent = mockk<Event>()
            val mockk = mockk<Visit> {
                every { event } returns visitEvent
            }
            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = mockk(),
                hibernatePatientRepository = mockk(),
                doctorRepository = mockk(),
                hibernateVisitRepository = mockk {
                    every { getById(uuid) } returns mockk
                },
                authorizer = authorizer
            )

            val result = eventDaoImpl.getEventVisit(VisitDto.VisitDtoId(uuid), mockk())

            assertEquals(visitEvent, result)
        }

        @Test
        fun editEvent() {
            val editInput = mockk<UpdateEventDto>(relaxed = true)
            val event = mockk<Event>(relaxed = true) {
                every { reoccurrence } returns null
                every { update(editInput, emptySet(), emptySet()) } just runs
            }
            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = mockk {
                    every { getById(editInput.eid.id) } returns event
                },
                hibernatePatientRepository = mockk {
                    every { findAllById(emptyList()) } returns emptyList()
                },
                doctorRepository = mockk {
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
            val eventDaoImpl = EventServiceImpl(
                hibernateEventRepository = mockk {
                    every {
                        getAllInRangeOrReoccurrenceIsNotNull(
                            rangeInput.start.toTimestamp(),
                            rangeInput.end.toTimestamp()
                        )
                    } returns events
                },
                hibernatePatientRepository = mockk(),
                doctorRepository = mockk(),
                hibernateVisitRepository = mockk(),
                authorizer = authorizer
            )

            val result = eventDaoImpl.getBetween(rangeInput, mockk())

            assertEquals(events, result)
        }
    }
}