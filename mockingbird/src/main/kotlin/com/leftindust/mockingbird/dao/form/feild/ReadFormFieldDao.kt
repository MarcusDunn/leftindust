package com.leftindust.mockingbird.dao.form.feild

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken
import com.leftindust.mockingbird.dao.entity.FormField
import com.leftindust.mockingbird.graphql.types.GraphQLFormSection
import com.leftindust.mockingbird.graphql.types.GraphQlFormField

interface ReadFormFieldDao {
    @Blocking
    fun getSectionFields(id: GraphQLFormSection.ID, authContext: MediqToken): List<FormField>
    @Blocking
    fun getFormFieldMultiSelectPossibilities(ffid: GraphQlFormField.ID, mediqAuthToken: MediqToken): List<String>?
}
