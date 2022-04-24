package com.leftindust.mockingbird.form

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken

interface ReadFormSectionDao {
    @Blocking
    fun getByTemplate(tid: GraphQLFormTemplate.ID, mediqAuthToken: MediqToken): List<FormSection>
}
