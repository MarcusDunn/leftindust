package com.leftindust.mockingbird.doctor

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.address.GraphQLAddress
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.event.EventDao
import com.leftindust.mockingbird.user.UserDao
import com.leftindust.mockingbird.address.ReadAddressDao
import com.leftindust.mockingbird.clinic.GraphQLClinic
import com.leftindust.mockingbird.email.GraphQLEmail
import com.leftindust.mockingbird.clinic.ReadClinicDao
import com.leftindust.mockingbird.email.ReadEmailDao
import com.leftindust.mockingbird.patient.ReadPatientDao
import com.leftindust.mockingbird.phone.ReadPhoneDao
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.graphql.types.*
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.person.GraphQLPerson
import com.leftindust.mockingbird.phone.GraphQLPhone
import com.leftindust.mockingbird.user.GraphQLUser
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.sql.Timestamp
import java.util.*

@GraphQLName("Doctor")
data class GraphQLDoctor(
    val did: ID,
    override val firstName: String,
    override val middleName: String? = null,
    override val lastName: String,
    override val thumbnail: String?,
    val title: String? = null,
    val dateOfBirth: GraphQLDate? = null,
) : GraphQLPerson {

    @GraphQLName("DoctorId")
    data class ID(val id: UUID)

    constructor(doctor: Doctor) : this(
        did = ID(doctor.id!!),
        firstName = doctor.nameInfo.firstName,
        middleName = doctor.nameInfo.middleName,
        lastName = doctor.nameInfo.lastName,
        thumbnail = doctor.thumbnail,
        title = doctor.title,
        dateOfBirth = doctor.dateOfBirth?.let { GraphQLDate(it.toLocalDate()) },
    )

    @GraphQLDescription("The user associated with this doctor, if it exists.")
    suspend fun user(
        @GraphQLIgnore @Autowired userDao: UserDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLUser? = withContext(Dispatchers.IO) {
        userDao.findByDoctor(did, dataFetchingEnvironment.authToken)
    }?.let(::GraphQLUser)

    @GraphQLDescription("The clinics this doctor is a member of.")
    suspend fun clinic(
        @GraphQLIgnore @Autowired clinicDao: ReadClinicDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLClinic> = withContext(Dispatchers.IO) {
        clinicDao.getByDoctor(did, dataFetchingEnvironment.authToken)
    }.map(::GraphQLClinic)

    @GraphQLDescription("The patients this doctor takes care of.")
    suspend fun patients(
        @GraphQLIgnore @Autowired patientDao: ReadPatientDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPatient> = withContext(Dispatchers.IO) {
        patientDao.getByDoctor(did, dataFetchingEnvironment.authToken)
    }.map(::GraphQLPatient)

    @GraphQLDescription("The events this doctor is a part of")
    suspend fun events(
        @GraphQLIgnore @Autowired eventDao: EventDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLEvent> = withContext(Dispatchers.IO) {
        eventDao.getByDoctor(did, dataFetchingEnvironment.authToken)
    }.map { event -> GraphQLEvent(event) }

    @GraphQLDescription("The events this doctor is a part of between two times")
    suspend fun schedule(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
        from: GraphQLUtcTime,
        to: GraphQLUtcTime
    ): List<GraphQLEvent> = withContext(Dispatchers.IO) {
        doctorDao.getByDoctor(did, dataFetchingEnvironment.authToken)
    }.getEventsBetween(Timestamp(from.unixMilliseconds), Timestamp(to.unixMilliseconds))
        .map { GraphQLEvent(event = it) }

    override suspend fun phones(
        @GraphQLIgnore @Autowired phoneDao: ReadPhoneDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPhone> = withContext(Dispatchers.IO) {
        phoneDao.getDoctorPhones(did, dataFetchingEnvironment.authToken)
    }.map(::GraphQLPhone)

    override suspend fun emails(
        @GraphQLIgnore @Autowired emailDao: ReadEmailDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLEmail> = withContext(Dispatchers.IO) {
        emailDao.getDoctorEmails(did, dataFetchingEnvironment.authToken)
    }.map(::GraphQLEmail)

    suspend fun addresses(
        @GraphQLIgnore @Autowired addressDao: ReadAddressDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLAddress> = withContext(Dispatchers.IO) {
        addressDao.getDoctorAddresses(did, dataFetchingEnvironment.authToken)
    }.map(::GraphQLAddress)
}