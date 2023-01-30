package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.graphql.types.Deletable
import com.leftindust.mockingbird.graphql.types.Updatable
import com.leftindust.mockingbird.graphql.types.input.DeletableMapDelegate
import com.leftindust.mockingbird.graphql.types.input.UpdatableMapDelegate
import com.leftindust.mockingbird.graphql.types.toDeletable
import com.leftindust.mockingbird.graphql.types.toUpdatable
import org.springframework.graphql.data.ArgumentValue

class MapDelegatingUpdateNameInfoDto(val map: Map<String, Any?>) : UpdateNameInfoDto {
    override val firstName: Updatable<String> by UpdatableMapDelegate(map)
    override val middleName: Deletable<String> by DeletableMapDelegate(map)
    override val lastName: Updatable<String> by UpdatableMapDelegate(map)
}

interface UpdateNameInfoDto : UpdateNameInfo {
    override val firstName: Updatable<String>
    override val middleName: Deletable<String>
    override val lastName: Updatable<String>
}

data class UpdateNameInfoGraphQlDto(
    val firstName: ArgumentValue<String> = ArgumentValue.omitted(),
    val middleName: ArgumentValue<String> = ArgumentValue.omitted(),
    val lastName: ArgumentValue<String> = ArgumentValue.omitted()
)

fun UpdateNameInfoGraphQlDto.toUpdateNameInfoDto(): UpdateNameInfoDto {

    return UpdateNameInfoDtoImpl(
        firstName = this.firstName.toUpdatable(),
        middleName = this.middleName.toDeletable(),
        lastName = this.lastName.toUpdatable()
    )
}

private data class UpdateNameInfoDtoImpl(
    override val firstName: Updatable<String>,
    override val middleName: Deletable<String>,
    override val lastName: Updatable<String>
) : UpdateNameInfoDto {


}