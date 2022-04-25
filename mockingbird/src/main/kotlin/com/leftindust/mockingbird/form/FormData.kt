package com.leftindust.mockingbird.form

import com.google.gson.JsonElement
import com.leftindust.mockingbird.persistance.JsonElementConverter
import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.patient.Patient
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class FormData(
    @Convert(converter = JsonElementConverter::class)
    @Column(length = 50_000)
    val data: JsonElement,
    @ManyToOne
    val patient: Patient,
) : AbstractJpaPersistable()