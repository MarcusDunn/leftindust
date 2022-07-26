package com.leftindust.mockingbird.icd

import com.leftindust.mockingbird.config.IcdApiClientConfiguration
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.serialization.jackson.jackson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class IcdFetcherServiceImpl(
    @Autowired private val config: IcdApiClientConfiguration,
) : IcdFetcherService {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson()
        }
        expectSuccess = true
    }

    override suspend fun linearization(
        code: GraphQLFoundationIcdCode,
        linearization: IcdFetcherService.Linearization,
    ): GraphQLIcdMultiVersion {
        val url = "${config.url}/release/11/${linearization.value}/${code.code}"
        return GraphQLIcdMultiVersion(getUrlWithIcdHeaders(url))
    }

    override suspend fun linearizationSearch(
        query: String,
        linearization: IcdFetcherService.Linearization,
        flatResults: Boolean,
        flexiSearch: Boolean,
    ): GraphQLIcdSearchResult {
        val url = "${config.url}/release/11/${GraphQLReleaseIdInput.CURRENT}/${linearization.value}/search?q=$query&flatResult=$flatResults&useFlexisearch=$flexiSearch"
        return getUrlWithIcdHeaders(url)
    }

    override suspend fun getDetails(
        code: GraphQLFoundationIcdCode,
    ): GraphQLIcdFoundationEntity {
        val url = "${config.url}/entity/${code.code}"
        return GraphQLIcdFoundationEntity(getUrlWithIcdHeaders(url))
    }

    override suspend fun search(
        query: String,
        flexiSearch: Boolean,
        flatResults: Boolean,
    ): GraphQLIcdSearchResult {
        val url = "${config.url}/entity/search?q=$query&useFlexisearch=$flexiSearch&flatResults=$flatResults"
        return getUrlWithIcdHeaders(url)
    }

    override suspend fun linearizationEntity(
        code: GraphQLFoundationIcdCode,
    ): GraphQLIcdLinearizationEntity {
        val url = "${config.url}/release/11/${GraphQLReleaseIdInput.CURRENT}/mms/${code.code}"
        return GraphQLIcdLinearizationEntity(getUrlWithIcdHeaders(url))
    }

    private suspend inline fun <reified T> getUrlWithIcdHeaders(url: String): T = client.get {
        url(url)
        header("Accept", "application/json")
        header("Accept-Language", "en")
        header("API-Version", "v2")
    }.body()
}