package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.MediqUser
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLUserEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLUserInput

interface UserDao {
    @Blocking
    fun findUserByUid(uid: String, requester: MediqToken): MediqUser?

    @Blocking
    fun getUserByUid(uid: String, requester: MediqToken): MediqUser

    @Blocking
    fun addUser(user: GraphQLUserInput, requester: MediqToken): MediqUser

    @Blocking
    fun getUsers(range: GraphQLRangeInput, requester: MediqToken): Collection<MediqUser>

    @Blocking
    fun updateUser(user: GraphQLUserEditInput, requester: MediqToken): MediqUser

    @Blocking
    fun findByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): MediqUser?

    @Blocking
    fun findPatientUser(pid: GraphQLPatient.ID, requester: MediqToken): MediqUser?
}