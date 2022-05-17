package com.leftindust.mockingbird.record

import javax.transaction.Transactional
import org.springframework.stereotype.Service


@Service
@Transactional
class ReadRecordServiceImpl : ReadRecordService