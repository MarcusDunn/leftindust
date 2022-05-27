package com.leftindust.mockingbird.person

import com.leftindust.mockingbird.user.HibernateUserRepository
import com.leftindust.mockingbird.user.MediqUserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ReadNameInfoServiceImpl(
    @Autowired private val hibernateUserRepository: HibernateUserRepository,
) : ReadNameInfoService {
    override fun getByUniqueId(mediqUserUniqueId: MediqUserDto.MediqUserUniqueId): NameInfo? {
        TODO("Not yet implemented")
    }
}