package com.leftindust.mockingbird.dao

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.Doctor
import com.leftindust.mockingbird.dao.entity.MediqUser
import com.leftindust.mockingbird.graphql.types.GraphQLClinic
import com.leftindust.mockingbird.graphql.types.GraphQLDoctor
import com.leftindust.mockingbird.graphql.types.GraphQLEvent
import com.leftindust.mockingbird.patient.GraphQLPatient
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorEditInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLDoctorInput
import com.leftindust.mockingbird.graphql.types.input.GraphQLRangeInput
import com.leftindust.mockingbird.graphql.types.search.example.GraphQLDoctorExample

interface DoctorDao {
    @Blocking
    fun getPatientDoctors(pid: GraphQLPatient.ID, requester: MediqToken): Collection<Doctor>

    @Blocking
    fun getByEvent(eid: GraphQLEvent.ID, requester: MediqToken): Collection<Doctor>

    @Blocking
    fun getByDoctor(did: GraphQLDoctor.ID, requester: MediqToken): Doctor

    @Blocking
    fun addDoctor(doctor: GraphQLDoctorInput, requester: MediqToken, user: MediqUser? = null): Doctor

    @Blocking
    fun editDoctor(doctor: GraphQLDoctorEditInput, requester: MediqToken): Doctor

    @Blocking
    fun getByClinic(clinic: GraphQLClinic.ID, requester: MediqToken): Collection<Doctor>

    @Blocking
    fun getByUser(uid: String, requester: MediqToken): Doctor?

    @Blocking
    fun getMany(range: GraphQLRangeInput, requester: MediqToken): Collection<Doctor>

    @Blocking
    fun searchByExample(example: GraphQLDoctorExample, requester: MediqToken): Collection<Doctor>
}