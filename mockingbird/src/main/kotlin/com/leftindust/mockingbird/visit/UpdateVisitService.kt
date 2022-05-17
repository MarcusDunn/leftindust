package com.leftindust.mockingbird.visit

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('UPDATE_VISIT')")
interface UpdateVisitService {
    fun editVisit(visit: GraphQLVisitEditInput): Visit
}