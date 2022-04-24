package com.leftindust.mockingbird.patient

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.leftindust.mockingbird.address.GraphQLAddress
import com.leftindust.mockingbird.auth.authToken
import com.leftindust.mockingbird.address.ReadAddressDao
import com.leftindust.mockingbird.contact.ContactDao
import com.leftindust.mockingbird.contact.GraphQLEmergencyContact
import com.leftindust.mockingbird.email.GraphQLEmail
import com.leftindust.mockingbird.email.ReadEmailDao
import com.leftindust.mockingbird.person.Ethnicity
import com.leftindust.mockingbird.person.Sex
import com.leftindust.mockingbird.phone.ReadPhoneDao
import com.leftindust.mockingbird.doctor.DoctorDao
import com.leftindust.mockingbird.doctor.GraphQLDoctor
import com.leftindust.mockingbird.event.EventDao
import com.leftindust.mockingbird.event.GraphQLEvent
import com.leftindust.mockingbird.form.GraphQLAssignedForm
import com.leftindust.mockingbird.form.ReadFormDao
import com.leftindust.mockingbird.graphql.types.*
import com.leftindust.mockingbird.person.GraphQLPerson
import com.leftindust.mockingbird.phone.GraphQLPhone
import com.leftindust.mockingbird.user.GraphQLUser
import com.leftindust.mockingbird.user.UserDao
import com.leftindust.mockingbird.visit.GraphQLVisit
import com.leftindust.mockingbird.visit.VisitDao
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLName("Patient")
data class GraphQLPatient(
    override val firstName: String,
    override val middleName: String? = null,
    override val lastName: String,
    @GraphQLDescription(GraphQLPerson.thumbnailDescription)
    override val thumbnail: String? = null,
    val pid: ID,
    val dateOfBirth: GraphQLDate,
    val insuranceNumber: String? = null,
    val sex: Sex,
    val gender: String = sex.toString(),
    val ethnicity: Ethnicity? = null,
) : GraphQLPerson {

    @GraphQLName("PatientId")
    data class ID(val id: UUID)

    constructor(patient: Patient) : this(
        pid = ID(patient.id!!),
        firstName = patient.nameInfo.firstName,
        middleName = patient.nameInfo.middleName,
        lastName = patient.nameInfo.lastName,
        dateOfBirth = GraphQLDate(patient.dateOfBirth.toLocalDate()),
        insuranceNumber = patient.insuranceNumber,
        sex = patient.sex,
        gender = patient.gender,
        ethnicity = patient.ethnicity,
        thumbnail = patient.thumbnail,
    )

    suspend fun contacts(
        @GraphQLIgnore @Autowired contactDao: ContactDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLPerson> = withContext(Dispatchers.IO) {
        contactDao.getPatientContacts(pid, dataFetchingEnvironment.authToken)
    }.map { GraphQLEmergencyContact(it) }

    suspend fun doctors(
        @GraphQLIgnore @Autowired doctorDao: DoctorDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLDoctor> = withContext(Dispatchers.IO) {
        doctorDao.getPatientDoctors(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLDoctor)

    suspend fun visits(
        @GraphQLIgnore @Autowired visitDao: VisitDao,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): List<GraphQLVisit> = withContext(Dispatchers.IO) {
        visitDao.getPatientVisits(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLVisit)

    suspend fun user(
        @GraphQLIgnore @Autowired userDao: UserDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): GraphQLUser? = withContext(Dispatchers.IO) {
        userDao.findPatientUser(pid, dataFetchingEnvironment.authToken)
    }?.let(::GraphQLUser)

    suspend fun events(
        @GraphQLIgnore @Autowired eventDao: EventDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLEvent> = withContext(Dispatchers.IO) {
        eventDao.getPatientEvents(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLEvent)

    suspend fun assignedForms(
        @GraphQLIgnore @Autowired formDao: ReadFormDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLAssignedForm> = withContext(Dispatchers.IO) {
        formDao.getByPatientAssigned(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLAssignedForm)

    override suspend fun phones(
        @GraphQLIgnore @Autowired phoneDao: ReadPhoneDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLPhone> = withContext(Dispatchers.IO) {
        phoneDao.getPatientPhones(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLPhone)

    override suspend fun emails(
        @GraphQLIgnore @Autowired emailDao: ReadEmailDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLEmail> = withContext(Dispatchers.IO) {
        emailDao.getPatientEmails(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLEmail)

    suspend fun addresses(
        @GraphQLIgnore @Autowired addressDao: ReadAddressDao,
        dataFetchingEnvironment: DataFetchingEnvironment,
    ): List<GraphQLAddress> = withContext(Dispatchers.IO) {
        addressDao.getPatientAddresses(pid, dataFetchingEnvironment.authToken)
    }.map(::GraphQLAddress)
}