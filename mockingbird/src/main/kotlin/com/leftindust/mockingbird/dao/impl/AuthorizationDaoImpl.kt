package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.dao.AuthorizationDao
import com.leftindust.mockingbird.dao.entity.AccessControlList
import com.leftindust.mockingbird.dao.impl.repository.HibernateAclRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateUserRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class AuthorizationDaoImpl(
    private val aclRepository: HibernateAclRepository,
    private val userRepository: HibernateUserRepository
) : AuthorizationDao {
    override fun getRolesForUserByUid(uid: String): List<AccessControlList> {
        val user = userRepository.findByUniqueId(uid) ?: return emptyList()
        val userPerms = aclRepository.findAllByMediqUser(user)
        val groupPerms = user.group?.let { aclRepository.findAllByGroup(it) } ?: emptyList()
        return userPerms + groupPerms
    }

    override fun isAdmin(uid: String): Boolean =
        adminAlias.contains(userRepository.findByUniqueId(uid)?.group?.name)

    override fun isPatient(uid: String): Boolean =
        patientAlias.contains(userRepository.findByUniqueId(uid)?.group?.name)

    val adminAlias = listOf("admin", "Administrator")
    val patientAlias = listOf("patient", "Patient")
}