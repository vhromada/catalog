package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.SongConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeSongRequest
import com.github.vhromada.catalog.web.connector.entity.Song
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for songs.
 *
 * @author Vladimir Hromada
 */
@Component("songConnector")
class SongConnectorImpl(
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
), SongConnector {

    override fun search(music: String, filter: PagingFilter): Page<Song> {
        val url = UriComponentsBuilder.fromUriString(getUrl(music = music))
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Song>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(music: String, uuid: String): Song {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(music = music, uuid = uuid), responseType = Song::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(music: String, request: ChangeSongRequest): Song {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(music = music), entity = request, responseType = Song::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(music: String, uuid: String, request: ChangeSongRequest): Song {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(music = music, uuid = uuid), entity = request, responseType = Song::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(music: String, uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(music = music, uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(music: String, uuid: String): Song {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(music = music, uuid = uuid)}/duplicate", responseType = Song::class.java))
            .throwExceptionIfAny()
            .get()
    }

    /**
     * Returns URL for music's UUID.
     *
     * @param music music's UUID
     * @return URL for music's UUID
     */
    private fun getUrl(music: String): String {
        return "${getUrl()}/rest/music/${music}/songs"
    }

    /**
     * Returns URL for music's UUID and song's UUID.
     *
     * @param music music's UUID
     * @param uuid song's UUID
     * @return URL for music's UUID and song's UUID
     */
    private fun getUrl(music: String, uuid: String): String {
        return "${getUrl(music = music)}/${uuid}"
    }

}
