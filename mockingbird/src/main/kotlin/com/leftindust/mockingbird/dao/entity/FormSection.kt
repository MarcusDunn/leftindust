package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.input.GraphQLFormSectionInput
import javax.persistence.*

@Entity
class FormSection private constructor(
    @OneToMany(orphanRemoval = true, cascade = [CascadeType.ALL])
    val fields: MutableSet<FormField>,
    @Column(length = 50_000)
    val description: String?,
    var name: String,
    val number: Int,
) : AbstractJpaPersistable() {
    constructor(
        name: String,
        number: Int,
        description: String?,
        fields: Set<FormField>,
    ) : this(
        name = name,
        number = number,
        description = description,
        fields = fields.toMutableSet(),
    )

    constructor(graphQLFormSectionInput: GraphQLFormSectionInput) : this(
        name = graphQLFormSectionInput.name,
        number = graphQLFormSectionInput.number,
        description = graphQLFormSectionInput.description,
        fields = graphQLFormSectionInput.fields.map { FormField(it) }.toSet()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as FormSection

        if (fields != other.fields) return false
        if (description != other.description) return false
        if (name != other.name) return false
        if (number != other.number) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + fields.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + number
        return result
    }


}
