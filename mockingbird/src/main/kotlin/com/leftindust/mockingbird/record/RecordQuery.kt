package com.leftindust.mockingbird.record

import org.springframework.stereotype.Controller

@Controller
class RecordQuery(
    private val readRecordService: ReadRecordService,
) {
    private fun recordByRecordId(recordId: RecordDto.RecordDtoId): RecordDto {
        TODO()
    }
}
