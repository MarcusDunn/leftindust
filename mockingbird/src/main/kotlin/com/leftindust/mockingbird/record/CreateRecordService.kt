package com.leftindust.mockingbird.record

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_RECORD')")
interface CreateRecordService {

}
