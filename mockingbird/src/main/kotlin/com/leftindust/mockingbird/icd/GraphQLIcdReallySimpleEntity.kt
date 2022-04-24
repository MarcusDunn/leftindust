package com.leftindust.mockingbird.icd

import com.expediagroup.graphql.generator.annotations.GraphQLName

@GraphQLName("IcdReallySimpleEntity")
interface GraphQLIcdReallySimpleEntity {
    val id: String?
    val code: String?
    val title: String?
    val description: String?
}