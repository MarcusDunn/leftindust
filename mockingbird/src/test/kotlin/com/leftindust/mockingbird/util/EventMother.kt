package com.leftindust.mockingbird.util

import com.leftindust.mockingbird.event.Event
import com.leftindust.mockingbird.event.EventDto
import java.util.UUID

object EventMother {
    private val eyeExamIcalString = """BEGIN:VCALENDAR
                             VERSION:2.0
                             PRODID:-//hacksw/handcal//NONSGML v1.0//EN
                             BEGIN:VEVENT
                             UID:uid1@example.com
                             DTSTAMP:19970714T170000Z
                             ORGANIZER;CN=John Doe:MAILTO:john.doe@example.com
                             DTSTART:19970714T170000Z
                             DTEND:19970715T040000Z
                             SUMMARY:Bastille Day Party
                             GEO:48.85299;2.36885
                             END:VEVENT
                             END:VCALENDAR""".trimIndent()
    val eyeExamUnpersisted: Event
        get() = Event(
            ical = eyeExamIcalString,
            doctors = mutableSetOf(),
            patients = mutableSetOf(),
            visit = null
        )

    val eyeExamPersisted
        get() = eyeExamUnpersisted.apply { id = eyeExamId.value }

    private val eyeExamId = EventDto.EventDtoId(UUID.randomUUID())
    val eyeExamDto
        get() = EventDto(
            id = eyeExamId,
            iCal = eyeExamIcalString
        )
}