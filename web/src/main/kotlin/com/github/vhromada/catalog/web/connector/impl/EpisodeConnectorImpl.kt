package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.EpisodeConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeEpisodeRequest
import com.github.vhromada.catalog.web.connector.entity.Episode
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for episodes.
 *
 * @author Vladimir Hromada
 */
@Component("episodeConnector")
class EpisodeConnectorImpl(
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
), EpisodeConnector {

    override fun search(show: String, season: String, filter: PagingFilter): Page<Episode> {
        val url = UriComponentsBuilder.fromUriString(getUrl(show = show, season = season))
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Episode>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(show: String, season: String, uuid: String): Episode {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(show = show, season = season, uuid = uuid), responseType = Episode::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(show: String, season: String, request: ChangeEpisodeRequest): Episode {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(show = show, season = season), entity = request, responseType = Episode::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(show: String, season: String, uuid: String, request: ChangeEpisodeRequest): Episode {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(show = show, season = season, uuid = uuid), entity = request, responseType = Episode::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(show: String, season: String, uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(show = show, season = season, uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(show: String, season: String, uuid: String): Episode {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(show = show, season = season, uuid = uuid)}/duplicate", responseType = Episode::class.java))
            .throwExceptionIfAny()
            .get()
    }

    /**
     * Returns URL for show's UUID and season's UUID.
     *
     * @param show   show's UUID
     * @param season season's UUID
     * @return URL for show's UUID and season's UUID
     */
    private fun getUrl(show: String, season: String): String {
        return "${getUrl()}/rest/shows/${show}/seasons/${season}/episodes"
    }

    /**
     * Returns URL for show's UUID, season's UUID and episode's UUID.
     *
     * @param show   show's UUID
     * @param season season's UUID
     * @param uuid   episode's UUID
     * @return URL for show's UUID, season's UUID and episode's UUID
     */
    private fun getUrl(show: String, season: String, uuid: String): String {
        return "${getUrl(show = show, season = season)}/${uuid}"
    }

}
