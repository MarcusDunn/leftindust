package com.leftindust.mockingbird.graphql.types

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.auth.GraphQLAuthContext
import com.leftindust.mockingbird.dao.DoctorDao
import com.leftindust.mockingbird.dao.EventDao
import com.leftindust.mockingbird.dao.UserDao
import com.leftindust.mockingbird.dao.address.ReadAddressDao
import com.leftindust.mockingbird.dao.clinic.ReadClinicDao
import com.leftindust.mockingbird.dao.email.ReadEmailDao
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.dao.patient.ReadPatientDao
import com.leftindust.mockingbird.dao.phone.ReadPhoneDao
import java.sql.Timestamp
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired

@GraphQLName("Doctor")
data class GraphQLDoctor(
    val did: ID,
    override val firstName: String,
    override val middleName: String? = null,
    override val lastName: String,
    override val thumbnail: String?,
    val title: String? = null,
    val dateOfBirth: GraphQLDate? = null,
    private val authContext: GraphQLAuthContext
) : GraphQLPerson {
    private val authToken = authContext.mediqAuthToken

    @GraphQLName("DoctorId")
    data class ID(val id: UUID)

    constructor(doctor: Doctor, authContext: GraphQLAuthContext) : this(
        did = ID(doctor.id!!),
        firstName = doctor.nameInfo.firstName,
        middleName = doctor.nameInfo.middleName,
        lastName = doctor.nameInfo.lastName,
        thumbnail = doctor.thumbnail,
        title = doctor.title,
        dateOfBirth = doctor.dateOfBirth?.let { GraphQLDate(it.toLocalDate()) },
        authContext = authContext
    )

    @GraphQLDescription("The user associated with this doctor, if it exists.")
    suspend fun user(@GraphQLIgnore @Autowired userDao: UserDao): GraphQLUser? = withContext(Dispatchers.IO) {
        userDao.findByDoctor(did, authContext.mediqAuthToken)
    }?.let { GraphQLUser(it, authContext) }

    @GraphQLDescription("The clinics this doctor is a member of.")
    suspend fun clinic(
        @GraphQLIgnore @Autowired clinicDao: ReadClinicDao
    ): List<GraphQLClinic> = withContext(Dispatchers.IO) {
        clinicDao.getByDoctor(did, authToken)
    }.map { GraphQLClinic(it, authContext) }

    @GraphQLDescription("The patients this doctor takes care of.")
    suspend fun patients(
        @GraphQLIgnore @Autowired patientDao: ReadPatientDao
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getByDoctor(did, authToken)
    }.map { GraphQLPatient(it, authContext) }

    @GraphQLDescription("The events this doctor is a part of")
    suspend fun events(
        @GraphQLIgnore @Autowired eventDao: EventDao
    ): List<GraphQLEvent> = withContext(Dispatchers.IO) {
        eventDao.getByDoctor(did, authContext.mediqAuthToken)
    }.map { event -> GraphQLEvent(event, authContext) }

    @GraphQLDescription("The events this doctor is a part of between two times")
    suspend fun schedule(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao,
        from: GraphQLUtcTime,
        to: GraphQLUtcTime
    ): List<GraphQLEvent> = withContext(Dispatchers.IO) {
        doctorDao.getByDoctor(did, authToken)
    }.getEventsBetween(Timestamp(from.unixMilliseconds), Timestamp(to.unixMilliseconds))
        .map { GraphQLEvent(event = it, authContext = authContext) }

    override suspend fun phones(
        @GraphQLIgnore @Autowired phoneDao: ReadPhoneDao
    ): List<GraphQLPhone> = withContext(Dispatchers.IO) {
        phoneDao.getDoctorPhones(did, authContext.mediqAuthToken)
    }.map { GraphQLPhone(it) }

    override suspend fun emails(
        @GraphQLIgnore @Autowired emailDao: ReadEmailDao
    ): List<GraphQLEmail> = withContext(Dispatchers.IO) {
        emailDao.getDoctorEmails(did, authContext.mediqAuthToken)
    }.map { GraphQLEmail(it) }

    suspend fun addresses(
        @GraphQLIgnore @Autowired addressDao: ReadAddressDao
    ): List<GraphQLAddress> = withContext(Dispatchers.IO) {
        addressDao.getDoctorAddresses(did, authContext.mediqAuthToken)
    }.map { GraphQLAddress(it) }
}