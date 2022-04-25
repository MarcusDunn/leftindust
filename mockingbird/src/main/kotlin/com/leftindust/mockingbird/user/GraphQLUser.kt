package com.leftindust.mockingbird.user

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.auth.AuthorizationDao
import com.leftindust.mockingbird.doctor.DoctorDao
import com.leftindust.mockingbird.person.NameInfoDao
import com.leftindust.mockingbird.auth.Action
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.person.GraphQLNameInfo
import com.leftindust.mockingbird.auth.GraphQLPermissions
import com.leftindust.mockingbird.patient.ReadPatientDao
import com.leftindust.mockingbird.auth.GraphQLPermissionInput
import com.leftindust.mockingbird.patient.GraphQLPatient
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("User")
data class GraphQLUser(
    val uid: String,
    val group: GraphQLUserGroup? = null
) {
    constructor(mediqUser: MediqUser) : this(
        uid = mediqUser.uniqueId,
        group = mediqUser.group?.let { GraphQLUserGroup(it) },
    )

    @GraphQLDescription(
        """
        The name of the user.
    """
    )
    suspend fun name(
        @GraphQLIgnore @Autowired nameInfoDao: NameInfoDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLNameInfo? = withContext(Dispatchers.IO) {
        nameInfoDao.findByUniqueId(uid, dataFetchingEnvironment.authToken)
    }?.let(::GraphQLNameInfo)


    @GraphQLDescription(
        """
        Weather the user is registered in mockingbird.
    """
    )
    suspend fun isRegistered(
        @GraphQLIgnore @Autowired userDao: UserDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): Boolean = withContext(Dispatchers.IO) {
        userDao.findUserByUid(uid, dataFetchingEnvironment.authToken)
    }.let { it != null }


    @GraphQLDescription(
        """
        The firebase-specific info for this user.
    """
    )
    suspend fun firebaseUserInfo(
        @GraphQLIgnore @Autowired userFetcher: UserFetcher,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLFirebaseInfo = withContext(Dispatchers.IO) {
        userFetcher.getUserInfo(uid, dataFetchingEnvironment.authToken)
    }.let(::GraphQLFirebaseInfo)

    @GraphQLDescription(
        """
        The permissions this user possesses
    """
    )
    suspend fun permissions(
        @GraphQLIgnore @Autowired authorizationDao: AuthorizationDao
    ): GraphQLPermissions = withContext(Dispatchers.IO) {
        authorizationDao.getRolesForUserByUid(uid)
    }.let(::GraphQLPermissions)


    @GraphQLDescription(
        """
        The corresponding doctor for this user if it exists.
    """
    )
    suspend fun doctor(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLDoctor? = withContext(Dispatchers.IO) {
        doctorDao.getByUser(uid, dataFetchingEnvironment.authToken)
    }?.let(::GraphQLDoctor)


    @GraphQLDescription(
        """
        The corresponding patient for this user if it exists.
    """
    )
    suspend fun patient(
        @GraphQLIgnore @Autowired patientDao: ReadPatientDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): GraphQLPatient? = withContext(Dispatchers.IO) {
        patientDao.getByUser(uid, dataFetchingEnvironment.authToken)
    }?.let(::GraphQLPatient)


    @GraphQLDescription(
        """
        Weather the user has permission do to a given action.
    """
    )
    suspend fun hasPermission(
        @GraphQLIgnore @Autowired authorizationDao: AuthorizationDao,
        perm: GraphQLPermissionInput
    ): Boolean = withContext(Dispatchers.IO) {
        authorizationDao.getRolesForUserByUid(uid)
    }.any { it.action.isSuperset(Action(perm)) }
}


