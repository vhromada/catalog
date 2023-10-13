package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.MusicConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeMusicRequest
import com.github.vhromada.catalog.web.connector.entity.Music
import com.github.vhromada.catalog.web.connector.entity.MusicStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for music.
 *
 * @author Vladimir Hromada
 */
@Component("musicConnector")
class MusicConnectorImpl(
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
), MusicConnector {

    override fun search(filter: NameFilter): Page<Music> {
        val url = UriComponentsBuilder.fromUriString(getUrl())
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Music>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(uuid: String): Music {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(uuid = uuid), responseType = Music::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(request: ChangeMusicRequest): Music {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(), entity = request, responseType = Music::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(uuid: String, request: ChangeMusicRequest): Music {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(uuid = uuid), entity = request, responseType = Music::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(uuid: String): Music {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(uuid = uuid)}/duplicate", responseType = Music::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getStatistics(): MusicStatistics {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/statistics", responseType = MusicStatistics::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getUrl(): String {
        return super.getUrl() + "/rest/music"
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
