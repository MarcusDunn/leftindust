package com.leftindust.mockingbird.auth

import com.leftindust.mockingbird.persistance.PermissionDao
import com.leftindust.mockingbird.persistance.AbstractHibernateDao
import com.leftindust.mockingbird.group.HibernateGroupRepository
import com.leftindust.mockingbird.user.HibernateUserRepository
import com.leftindust.mockingbird.user.GraphQLUserGroup
import javax.transaction.Transactional
import org.springframework.stereotype.Repository

@Transactional
@Repository
class PermissionDaoImpl(
    private val groupRepository: HibernateGroupRepository,
    private val aclRepository: HibernateAclRepository,
    private val userRepository: HibernateUserRepository,
    authorizer: Authorizer,
) : PermissionDao, AbstractHibernateDao(authorizer) {
    companion object {
        private val createAcl = Crud.CREATE to Tables.AccessControlList
    }

    override fun addUserPermission(
        uid: String,
        permission: GraphQLPermissionInput,
        requester: MediqToken
    ): AccessControlList = if (requester can createAcl) {
        val user = userRepository.getByUniqueId(uid)
        val action = Action(permission)
        val acl = AccessControlList(mediqUser = user, action = action)
        aclRepository.save(acl)
    } else {
        throw NotAuthorizedException(requester, createAcl)
    }

    override fun addGroupPermission(
        gid: GraphQLUserGroup.ID,
        permission: GraphQLPermissionInput,
        requester: MediqToken
    ): AccessControlList = if (requester can createAcl) {
        val group = groupRepository.getById(gid.id)
        val action = Action(permission)
        val acl = AccessControlList(group = group, action = action)
        aclRepository.save(acl)
    } else {
        throw NotAuthorizedException(requester, createAcl)
    }
}