package com.leftindust.mockingbird.dao.entity

import javax.persistence.Embeddable

@Embeddable
class Measurement(
    var magnitude: Float,
    var unit: String,
)