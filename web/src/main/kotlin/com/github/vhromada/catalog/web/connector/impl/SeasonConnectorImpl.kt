package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.SeasonConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeSeasonRequest
import com.github.vhromada.catalog.web.connector.entity.Season
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for seasons.
 *
 * @author Vladimir Hromada
 */
@Component("seasonConnector")
class SeasonConnectorImpl(
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
), SeasonConnector {

    override fun search(show: String, filter: PagingFilter): Page<Season> {
        val url = UriComponentsBuilder.fromUriString(getUrl(show = show))
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Season>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(show: String, uuid: String): Season {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(show = show, uuid = uuid), responseType = Season::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(show: String, request: ChangeSeasonRequest): Season {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(show = show), entity = request, responseType = Season::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(show: String, uuid: String, request: ChangeSeasonRequest): Season {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(show = show, uuid = uuid), entity = request, responseType = Season::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(show: String, uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(show = show, uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(show: String, uuid: String): Season {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(show = show, uuid = uuid)}/duplicate", responseType = Season::class.java))
            .throwExceptionIfAny()
            .get()
    }

    /**
     * Returns URL for show's UUID.
     *
     * @param show show's UUID
     * @return URL for show's UUID
     */
    private fun getUrl(show: String): String {
        return "${getUrl()}/rest/shows/${show}/seasons"
    }

    /**
     * Returns URL for show's UUID and season's UUID.
     *
     * @param show show's UUID
     * @param uuid season's UUID
     * @return URL for show's UUID and season's UUID
     */
    private fun getUrl(show: String, uuid: String): String {
        return "${getUrl(show = show)}/${uuid}"
    }

}
