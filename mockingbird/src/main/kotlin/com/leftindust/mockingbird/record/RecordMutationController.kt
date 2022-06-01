package com.leftindust.mockingbird.record

import org.springframework.stereotype.Component

@Component
class RecordMutationController(
    private val createRecordService: CreateRecordService,
) {
    suspend fun addRecord(record: CreateRecordDto): RecordDto = TODO()
}