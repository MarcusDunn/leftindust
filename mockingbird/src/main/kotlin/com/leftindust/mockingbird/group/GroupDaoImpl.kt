package com.leftindust.mockingbird.group

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.auth.Tables
import com.leftindust.mockingbird.persistance.AbstractHibernateDao
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.user.GraphQLUserGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class GroupDaoImpl(
    @Autowired authorizer: Authorizer,
    @Autowired private val groupRepository: HibernateGroupRepository,
) : GroupDao, AbstractHibernateDao(authorizer) {
    override fun addGroup(group: GraphQLGroupInput, requester: MediqToken): MediqGroup {
        if (requester can (Crud.CREATE to Tables.Group)) {
            val groupEntity = MediqGroup(group)
            return groupRepository.save(groupEntity)
        } else {
            throw NotAuthorizedException(requester, Crud.CREATE to Tables.Group)
        }
    }

    override fun getGroupById(gid: GraphQLUserGroup.ID, requester: MediqToken): MediqGroup =
        if (requester can (Crud.READ to Tables.Group)) {
            groupRepository.getById(gid.id)
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.Group)
        }

    override fun getRange(range: GraphQLRangeInput, requester: MediqToken): Collection<MediqGroup> =
        if (requester can (Crud.READ to Tables.Group)) {
            groupRepository.findAll(range.toPageable()).content
        } else {
            throw NotAuthorizedException(requester, Crud.READ to Tables.Group)
        }
}