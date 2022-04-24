package com.leftindust.mockingbird.form

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import java.sql.Date
import javax.persistence.*

/**
 * describes a single field in a form. A field has a [dataType] that determines optional restrictions on the data
 */
@Entity
class FormField private constructor(
    val title: String,
    val number: Int,
    @Enumerated(value = EnumType.STRING)
    val dataType: DataType,
    @ElementCollection
    val multiSelectPossibilities: List<String>? = null,
    @Column(nullable = true)
    val intUpperBound: Int? = null,
    @Column(nullable = true)
    val intLowerBound: Int? = null,
    @Column(nullable = true)
    val floatUpperBound: Float? = null,
    @Column(nullable = true)
    val floatLowerBound: Float? = null,
    @Column(nullable = true)
    val dateUpperBound: Date? = null,
    @Column(nullable = true)
    val dateLowerBound: Date? = null,
    @Column(nullable = true)
    val textRegex: String? = null,
    @Column(nullable = true, length = 5_000)
    var jsonMetaData: String? = null,
) : AbstractJpaPersistable() {

    constructor(
        title: String,
        number: Int,
        dataType: DataType,
        dateUpperBound: Date?,
        dateLowerBound: Date?,
        jsonMetaData: String? = null,
    ) : this(
        title = title,
        number = number,
        dataType = dataType,
        dateUpperBound = dateUpperBound,
        dateLowerBound = dateLowerBound,
        jsonMetaData = jsonMetaData,
        textRegex = null,
    ) {
        if (dataType != DataType.Date) {
            throw IllegalArgumentException("illegal arguments for formFeild of type $dataType")
        }
    }

    constructor(
        title: String,
        number: Int,
        dataType: DataType,
        multiSelectPossibilities: List<String>?,
        jsonMetaData: String? = null,
    ) : this(
        title = title,
        number = number,
        dataType = dataType,
        multiSelectPossibilities = multiSelectPossibilities,
        jsonMetaData = jsonMetaData,
        textRegex = null,
    ) {
        if (dataType != DataType.MultiMultiSelect && dataType != DataType.SingleMultiSelect) {
            throw IllegalArgumentException("illegal arguments for formFeild of type $dataType")
        }
    }

    constructor(graphQlFormFieldInput: GraphQlFormFieldInput) : this(
        title = graphQlFormFieldInput.title,
        number = graphQlFormFieldInput.number,
        dataType = graphQlFormFieldInput.dataType,
        multiSelectPossibilities = graphQlFormFieldInput.multiSelectPossibilities,
        intUpperBound = graphQlFormFieldInput.intUpperBound,
        intLowerBound = graphQlFormFieldInput.intLowerBound,
        floatUpperBound = graphQlFormFieldInput.floatUpperBound,
        floatLowerBound = graphQlFormFieldInput.floatLowerBound,
        dateUpperBound = graphQlFormFieldInput.dateUpperBound?.toDate(),
        dateLowerBound = graphQlFormFieldInput.dateLowerBound?.toDate(),
        textRegex = graphQlFormFieldInput.textRegex,
        jsonMetaData = graphQlFormFieldInput.jsonMetaData,
    )
}


