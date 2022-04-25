package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.persistance.AbstractJpaPersistable
import com.leftindust.mockingbird.extensions.onUndefined
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class NameInfo(
    @Column(nullable = false)
    var firstName: String,
    @Column(nullable = false)
    var lastName: String,
    @Column(nullable = true)
    var middleName: String? = null,
) : AbstractJpaPersistable() {
    constructor(graphQLNameInput: GraphQLNameInfoInput) : this(
        firstName = graphQLNameInput.firstName,
        lastName = graphQLNameInput.lastName,
        middleName = graphQLNameInput.middleName,
    )

    override fun toString(): String {
        return "NameInfo(firstName='$firstName', lastName='$lastName', middleName=$middleName)"
    }

    fun setByGqlInput(nameInfoEditInput: GraphQLNameInfoEditInput?) {
        // only updates on NN nameInfoEditInput
        nameInfoEditInput?.let {
            firstName = it.firstName ?: firstName
            middleName = it.middleName.onUndefined(middleName)
            lastName = it.lastName ?: lastName
        }
    }
}
