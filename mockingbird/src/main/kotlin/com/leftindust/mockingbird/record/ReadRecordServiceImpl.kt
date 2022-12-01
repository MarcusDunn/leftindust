package com.leftindust.mockingbird.record

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
@Transactional
class ReadRecordServiceImpl : ReadRecordService