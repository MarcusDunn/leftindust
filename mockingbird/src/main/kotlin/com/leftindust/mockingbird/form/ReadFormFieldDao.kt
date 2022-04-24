package com.leftindust.mockingbird.form

import com.leftindust.mockingbird.Blocking
import com.leftindust.mockingbird.auth.MediqToken

interface ReadFormFieldDao {
    @Blocking
    fun getSectionFields(id: GraphQLFormSection.ID, authContext: MediqToken): List<FormField>

    @Blocking
    fun getFormFieldMultiSelectPossibilities(ffid: GraphQlFormField.ID, mediqAuthToken: MediqToken): List<String>?
}
