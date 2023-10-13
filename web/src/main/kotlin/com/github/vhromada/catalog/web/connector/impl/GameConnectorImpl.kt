package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.GameConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeGameRequest
import com.github.vhromada.catalog.web.connector.entity.Game
import com.github.vhromada.catalog.web.connector.entity.GameStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for games.
 *
 * @author Vladimir Hromada
 */
@Component("gameConnector")
class GameConnectorImpl(
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
), GameConnector {

    override fun search(filter: NameFilter): Page<Game> {
        val url = UriComponentsBuilder.fromUriString(getUrl())
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Game>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(uuid: String): Game {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(uuid = uuid), responseType = Game::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(request: ChangeGameRequest): Game {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(), entity = request, responseType = Game::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(uuid: String, request: ChangeGameRequest): Game {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(uuid = uuid), entity = request, responseType = Game::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(uuid: String): Game {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(uuid = uuid)}/duplicate", responseType = Game::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getStatistics(): GameStatistics {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/statistics", responseType = GameStatistics::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getUrl(): String {
        return super.getUrl() + "/rest/games"
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
