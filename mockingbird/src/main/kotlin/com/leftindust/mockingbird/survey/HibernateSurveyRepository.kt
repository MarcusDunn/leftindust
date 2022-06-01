package com.leftindust.mockingbird.survey

import java.util.*
import org.springframework.data.repository.CrudRepository

interface HibernateSurveyRepository : CrudRepository<Survey, UUID>