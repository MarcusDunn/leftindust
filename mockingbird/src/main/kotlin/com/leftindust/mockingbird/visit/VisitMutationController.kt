package com.leftindust.mockingbird.visit

import org.springframework.stereotype.Component

@Component
class VisitMutationController(
    private val updateVisitService: UpdateVisitService,
    private val createVisitService: CreateVisitService,
) {
    suspend fun addVisit(visit: GraphQLVisitInput): VisitDto {
        TODO()
    }

    suspend fun editVisit(visit: GraphQLVisitEditInput): VisitDto {
        TODO()
    }
}