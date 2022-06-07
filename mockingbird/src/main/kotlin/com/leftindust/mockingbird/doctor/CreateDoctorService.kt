package com.leftindust.mockingbird.doctor

interface CreateDoctorService {
    suspend fun addDoctor(createDoctor: CreateDoctor): Doctor
}
