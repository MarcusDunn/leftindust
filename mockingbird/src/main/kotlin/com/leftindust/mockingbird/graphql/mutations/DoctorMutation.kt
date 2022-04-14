package com.leftindust.mockingbird.graphql.mutations

import com.expediagroup.graphql.server.operations.Mutation
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorInput
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class DoctorMutation(private val doctorDao: DoctorDao, private val userDao: UserDao) : Mutation {
    suspend fun addDoctor(
        doctor: GraphQLDoctorInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLDoctor {
        val user = if (doctor.user != null) {
            withContext(Dispatchers.IO) {
                userDao.findUserByUid(doctor.user.uid, dataFetchingEnvironment.authToken)
                    ?: userDao.addUser(doctor.user, dataFetchingEnvironment.authToken)
            }
        } else null

        return withContext(Dispatchers.IO) {
            doctorDao.addDoctor(doctor, dataFetchingEnvironment.authToken, user = user)
        }.let(::GraphQLDoctor)
    }

    suspend fun editDoctor(
        doctor: GraphQLDoctorEditInput,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLDoctor = withContext(Dispatchers.IO) {
        doctorDao.editDoctor(doctor, dataFetchingEnvironment.authToken)
    }.let(::GraphQLDoctor)
}