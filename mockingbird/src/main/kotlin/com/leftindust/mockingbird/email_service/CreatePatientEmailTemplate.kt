package com.leftindust.mockingbird.email_service

internal class CreatePatientEmailTemplate(name: String, patientName: String): EmailTemplate {
    override val subject: String = "Patient Creation"
    override var html: String = "<h1>Hello ${name}</h1><p>A new patient with name ${patientName} has been created.</p>"
    override var text: String = "Hello ${name}, a new patient with name ${patientName} has been created."
}