package com.leftindust.mockingbird.user

import com.expediagroup.graphql.generator.execution.OptionalInput
import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.persistance.AbstractHibernateDao
import com.leftindust.mockingbird.doctor.HibernateDoctorRepository
import com.leftindust.mockingbird.group.HibernateGroupRepository
import com.leftindust.mockingbird.patient.HibernatePatientRepository
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UserDaoImpl(
    authorizer: Authorizer,
    private val userRepository: HibernateUserRepository,
    private val groupRepository: HibernateGroupRepository,
    private val doctorRepository: HibernateDoctorRepository,
    private val patientRepository: HibernatePatientRepository,
) : UserDao, AbstractHibernateDao(authorizer) {

    override fun findUserByUid(uid: String, requester: MediqToken): MediqUser? {
        return if (requester can (Crud.READ to Tables.User) || (uid == requester.uid && requester.isVerified())) {
            userRepository.findByUniqueId(uid)
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.User)
        }
    }

    override fun getUserByUid(uid: String, requester: MediqToken): MediqUser {
        return if (requester can (Crud.READ to Tables.User) || (uid == requester.uid && requester.isVerified())) {
            userRepository.getByUniqueId(uid)
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.User)
        }
    }

    override fun addUser(
        user: GraphQLUserInput,
        requester: MediqToken
    ): MediqUser = if (requester can (Crud.CREATE to Tables.User)) {
        val group = user.group?.let { groupRepository.getById(it.id) }
        val mediqUser = MediqUser(user, group)
        val userEntity = userRepository.save(mediqUser)
        if (user.doctor != null) {
            val doctor = doctorRepository.getById(user.doctor.id)
            doctor.user = userEntity
        }
        userEntity
    } else {
        throw NotAuthorizedException(requester, Crud.CREATE to Tables.User)
    }

    override fun getUsers(
        range: GraphQLRangeInput,
        requester: MediqToken
    ): Collection<MediqUser> = if (requester can (Crud.READ to Tables.User)) {
        userRepository.findAll(range.toPageable()).content
    } else {
        throw NotAuthorizedException(requester, Crud.READ to Tables.Patient)
    }

    override fun updateUser(user: GraphQLUserEditInput, requester: MediqToken): MediqUser =
        if (requester can (Crud.UPDATE to Tables.User)) {
            val userEntity = userRepository.getByUniqueId(user.uid).apply {
                group = when (user.group) {
                    is OptionalInput.Undefined -> this.group
                    is OptionalInput.Defined -> user.group.value?.let { groupRepository.getById(it.id) }
                }
            }
            when (user.doctor) {
                OptionalInput.Undefined -> {/* no-op */
                }
                is OptionalInput.Defined -> {
                    val doctor = doctorRepository.getByUser_UniqueId(user.uid)
                    if (user.doctor.value == null) {
                        doctor.user = null
                    } else {
                        doctor.user = userEntity
                    }
                }
            }
            userEntity
        } else {
            throw NotAuthorizedException(requester, Crud.UPDATE to Tables.Patient)
        }

    override fun findByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): MediqUser? {
        val permissions = listOf(Crud.READ to Tables.User, Crud.READ to Tables.Doctor)
        return if (requester can permissions) {
            doctorRepository.getById(did.id).user
        } else {
            throw NotAuthorizedException(requester, *permissions.toTypedArray())
        }
    }

    override fun findPatientUser(pid: GraphQLPatient.ID, requester: MediqToken): MediqUser? {
        val permissions = listOf(Crud.READ to Tables.User, Crud.READ to Tables.Patient)
        return if (requester can permissions) {
            patientRepository.getById(pid.id).user
        } else {
            throw NotAuthorizedException(requester, *permissions.toTypedArray())
        }
    }
}