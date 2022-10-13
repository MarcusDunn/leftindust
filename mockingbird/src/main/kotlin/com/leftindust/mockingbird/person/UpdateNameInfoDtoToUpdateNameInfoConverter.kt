package com.leftindust.mockingbird.person
import com.leftindust.mockingbird.graphql.types.Updatable

fun UpdateNameInfoDto.toUpdateNameInfo(): UpdateNameInfoImpl {
    return UpdateNameInfoImpl(
            firstName = Updatable.Update(firstName),
            middleName = Updatable.Update(middleName),
            lastName = Updatable.Update(lastName),
        )
}

data class UpdateNameInfoImpl(
    override val firstName: Updatable<String>,
    override val middleName: Updatable<String>,
    override val lastName: Updatable<String>
) : UpdateNameInfo