package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.contact.Contact
import com.leftindust.mockingbird.doctor.Doctor
import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.patient.Patient
import com.leftindust.mockingbird.patient.Patient_.ethnicity
import com.leftindust.mockingbird.patient.Patient_.insuranceNumber
import com.leftindust.mockingbird.person.NameInfo
import com.leftindust.mockingbird.person.Sex
import java.time.LocalDate
import java.time.Month
import java.util.UUID

object EventMother {
    private val eid =
        UUID.fromString("981c2f6d-5dfd-49a3-b7a1-bb0e01b93f20")

    val jennyAppointmentUnpersisted
        get() = Event(
            ical = "BEGIN:VCALENDAR\n" +
                    " PRODID:-//xyz Corp//NONSGML PDA Calendar Version 1.0//EN\n" +
                    " VERSION:2.0\n" +
                    " BEGIN:VEVENT\n" +
                    " DTSTAMP:19960704T120000Z\n" +
                    " UID:uid1@example.com\n" +
                    " ORGANIZER:mailto:jsmith@example.com\n" +
                    " DTSTART:19960918T143000Z\n" +
                    " DTEND:19960920T220000Z\n" +
                    " STATUS:CONFIRMED\n" +
                    " CATEGORIES:CONFERENCE\n" +
                    " SUMMARY:Networld+Interop Conference\n" +
                    " DESCRIPTION:Networld+Interop Conference\n" +
                    " and Exhibit\\nAtlanta World Congress Center\\n\n" +
                    " Atlanta\\, Georgia\n" +
                    " END:VEVENT\n" +
                    " END:VCALENDAR",
            doctors = mutableSetOf(),
            patients = mutableSetOf(),
            visit = null,
        )

    val jennyAppointmentPersisted
        get() = jennyAppointmentUnpersisted.apply { id = eid }
}