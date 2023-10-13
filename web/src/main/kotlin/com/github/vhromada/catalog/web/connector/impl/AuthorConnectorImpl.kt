package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.AuthorConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.Author
import com.github.vhromada.catalog.web.connector.entity.AuthorStatistics
import com.github.vhromada.catalog.web.connector.entity.ChangeAuthorRequest
import com.github.vhromada.catalog.web.connector.filter.AuthorFilter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorConnector")
class AuthorConnectorImpl(
    /**
     * Configuration for connector
     */
    connectorConfig: ConnectorConfig,
    /**
     * Error handler
     */
    errorHandler: ResponseErrorHandler,
    /**
     * Interceptor for logging
     */
    loggingInterceptor: RestLoggingInterceptor,
    /**
     * Interceptor for processing header X-User
     */
    userInterceptor: UserInterceptor
) : RestConnector(
    system = "CatalogWebSpring",
    config = connectorConfig,
    errorHandler = errorHandler,
    loggingInterceptor = loggingInterceptor,
    userInterceptor = userInterceptor
), AuthorConnector {

    override fun getAll(): List<Author> {
        val filter = AuthorFilter()
        filter.page = 1
        filter.limit = Int.MAX_VALUE
        return search(filter = filter).data
    }

    override fun search(filter: AuthorFilter): Page<Author> {
        val url = UriComponentsBuilder.fromUriString(getUrl())
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Author>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(uuid: String): Author {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(uuid = uuid), responseType = Author::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(request: ChangeAuthorRequest): Author {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(), entity = request, responseType = Author::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(uuid: String, request: ChangeAuthorRequest): Author {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(uuid = uuid), entity = request, responseType = Author::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(uuid: String): Author {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(uuid = uuid)}/duplicate", responseType = Author::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getStatistics(): AuthorStatistics {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/statistics", responseType = AuthorStatistics::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getUrl(): String {
        return super.getUrl() + "/rest/authors"
    }

    /**
     * Returns URL UUID.
     *
     * @param uuid UUID
     * @return URL for UUID
     */
    private fun getUrl(uuid: String): String {
        return "${getUrl()}/${uuid}"
    }

}
