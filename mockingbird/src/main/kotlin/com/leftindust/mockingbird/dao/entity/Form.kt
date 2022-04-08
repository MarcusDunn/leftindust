package com.leftindust.mockingbird.dao.entity

import com.leftindust.mockingbird.dao.entity.superclasses.AbstractJpaPersistable
import com.leftindust.mockingbird.graphql.types.input.GraphQLFormTemplateInput
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

/**
 * describes the format of a form. it DOES NOT hold data. It's instead a template for a record form.
 */
@Entity
class Form private constructor(
    var name: String,
    @OneToMany(
        orphanRemoval = true,
        cascade = [CascadeType.ALL]
    )
    val sections: MutableSet<FormSection>,
) : AbstractJpaPersistable() {
    constructor(sections: Set<FormSection>, name: String) : this(name = name, sections = sections.toMutableSet())
    constructor(form: GraphQLFormTemplateInput) : this(
        name = form.name,
        sections = form.sections.map { FormSection(it) }.toSet()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Form

        if (name != other.name) return false
        if (sections != other.sections) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + sections.hashCode()
        return result
    }
}
