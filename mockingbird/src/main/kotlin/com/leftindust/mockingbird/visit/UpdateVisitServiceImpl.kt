package com.leftindust.mockingbird.visit

import javax.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class UpdateVisitServiceImpl : UpdateVisitService {
    override fun editVisit(visit: GraphQLVisitEditInput): Visit {
        TODO("Not yet implemented")
    }
}