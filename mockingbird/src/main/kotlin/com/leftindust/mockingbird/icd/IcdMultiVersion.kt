package com.leftindust.mockingbird.icd

data class IcdMultiVersion(
    val `@context`: String?,
    val `@id`: String?,
    val title: IcdLanguageSpecificText,
    val latestRelease: String?,
    val release: List<String>
)