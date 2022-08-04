package com.leftindust.mockingbird.survey.complete

import java.util.UUID
import org.springframework.data.repository.CrudRepository

interface CompleteSurveySectionRepository : CrudRepository<CompleteSurveySectionEntity, UUID> {
}
