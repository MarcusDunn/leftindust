package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class DoctorMutation(private val doctorDao: DoctorDao, private val userDao: UserDao) : Mutation {
    suspend fun addDoctor(
        doctor: GraphQLDoctorInput,
        graphQLAuthContext: GraphQLAuthContext,
    ): GraphQLDoctor {
        val user = if (doctor.user != null) {
            withContext(Dispatchers.IO) {
                userDao.findUserByUid(doctor.user.uid, graphQLAuthContext.mediqAuthToken)
                    ?: userDao.addUser(doctor.user, graphQLAuthContext.mediqAuthToken)
            }
        } else null

        return withContext(Dispatchers.IO) {
            doctorDao.addDoctor(doctor, graphQLAuthContext.mediqAuthToken, user = user)
        }.let { GraphQLDoctor(it, graphQLAuthContext) }
    }

    suspend fun editDoctor(
        doctor: GraphQLDoctorEditInput, graphQLAuthContext: GraphQLAuthContext
    ): GraphQLDoctor = withContext(Dispatchers.IO) {
        doctorDao.editDoctor(doctor, graphQLAuthContext.mediqAuthToken)
    }.let { GraphQLDoctor(it, graphQLAuthContext) }
}