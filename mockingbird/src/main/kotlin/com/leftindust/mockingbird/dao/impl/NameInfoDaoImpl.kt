package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.NameInfoDao
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.entity.NameInfo
import com.leftindust.mockingbird.dao.impl.repository.HibernateUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class NameInfoDaoImpl(
    @Autowired private val hibernateUserRepository: HibernateUserRepository,
    @Autowired authorizer: Authorizer
) : NameInfoDao, AbstractHibernateDao(authorizer) {
    companion object {
        private val readUsers = Crud.READ to Tables.User
    }

    override fun findByUniqueId(uid: String, requester: MediqToken): NameInfo? =
        if (requester can readUsers) {
            hibernateUserRepository.findByUniqueId(uid)?.nameInfo
        } else {
            throw NotAuthorizedException(requester, readUsers)
        }
}