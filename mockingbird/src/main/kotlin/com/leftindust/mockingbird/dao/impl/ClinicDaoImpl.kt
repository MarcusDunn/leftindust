package com.leftindust.mockingbird.dao.impl

import com.leftindust.mockingbird.auth.Authorizer
import com.leftindust.mockingbird.auth.Crud
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.auth.NotAuthorizedException
import com.leftindust.mockingbird.dao.Tables
import com.leftindust.mockingbird.dao.clinic.CreateClinicDao
import com.leftindust.mockingbird.dao.clinic.DeleteClinicDao
import com.leftindust.mockingbird.dao.clinic.ReadClinicDao
import com.leftindust.mockingbird.dao.clinic.UpdateClinicDao
import com.leftindust.mockingbird.dao.entity.Clinic
import com.leftindust.mockingbird.dao.impl.repository.HibernateClinicRepository
import com.leftindust.mockingbird.dao.impl.repository.HibernateDoctorRepository
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLClinicInput
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@Repository
@Transactional
class ClinicDaoImpl(
    private val clinicRepository: HibernateClinicRepository,
    private val doctorRepository: HibernateDoctorRepository,
    private val entityManager: EntityManager,
    authorizer: Authorizer
) : ReadClinicDao, CreateClinicDao, UpdateClinicDao, DeleteClinicDao, AbstractHibernateDao(authorizer) {
    companion object {
        private val createClinic = Crud.CREATE to Tables.Clinic
        private val readClinic = Crud.READ to Tables.Clinic
    }

    override fun addClinic(clinic: GraphQLClinicInput, requester: MediqToken): Clinic =
        if (requester can createClinic) {
            clinicRepository.save(Clinic(clinic, entityManager))
        } else {
            throw NotAuthorizedException(requester, createClinic)
        }


    override fun editClinic(clinic: GraphQLClinicEditInput, requester: MediqToken): Clinic =
        if (requester can createClinic) {
            val clinicEntity = clinicRepository.getById(clinic.cid.id)
            clinicEntity.setByGqlInput(clinic, entityManager)
            clinicEntity
        } else {
            throw NotAuthorizedException(requester, createClinic)
        }


    override fun getByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): Collection<Clinic> =
        if (requester can readClinic) {
            doctorRepository.getById(did.id).clinics
        } else {
            throw NotAuthorizedException(requester, readClinic)
        }


    override fun getByCid(cid: GraphQLClinic.ID, requester: MediqToken): Clinic =
        if (requester can readClinic) {
            clinicRepository.getById(cid.id)
        } else {
            throw NotAuthorizedException(requester, readClinic)
        }

    override fun necessaryPermissions() = ReadClinicDao.necessaryPermissions +
            CreateClinicDao.necessaryPermissions +
            UpdateClinicDao.necessaryPermissions +
            DeleteClinicDao.necessaryPermissions
}