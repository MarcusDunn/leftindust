package com.leftindust.mockingbird.visit

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CreateVisitServiceImpl : CreateVisitService {
    override fun addVisit(visitInput: GraphQLVisitInput): Visit {
        TODO("Not yet implemented")
    }
}