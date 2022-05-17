package com.leftindust.mockingbird.visit

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasAuthority('CREATE_VISIT')")
interface CreateVisitService {
    fun addVisit(visitInput: GraphQLVisitInput): Visit
}