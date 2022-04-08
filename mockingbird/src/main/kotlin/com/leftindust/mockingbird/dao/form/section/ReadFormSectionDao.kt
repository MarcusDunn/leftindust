package com.leftindust.mockingbird.dao.form.section

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.FormSection
import com.leftindust.mockingbird.graphql.types.GraphQLFormTemplate

interface ReadFormSectionDao {
    @Blocking
    fun getByTemplate(tid: GraphQLFormTemplate.ID, mediqAuthToken: MediqToken): List<FormSection>
}
