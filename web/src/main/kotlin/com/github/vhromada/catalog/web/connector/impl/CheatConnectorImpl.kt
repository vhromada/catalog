package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.web.connector.CheatConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.RestResponse
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeCheatRequest
import com.github.vhromada.catalog.web.connector.entity.Cheat
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

/**
 * A class represents implementation of connector for cheats.
 *
 * @author Vladimir Hromada
 */
@Component("cheatConnector")
class CheatConnectorImpl(
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
), CheatConnector {

    override fun find(game: String): RestResponse<Cheat> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(game = game), responseType = Cheat::class.java))
    }

    override fun get(game: String, uuid: String): Cheat {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(game = game, uuid = uuid), responseType = Cheat::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(game: String, request: ChangeCheatRequest): Cheat {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(game = game), entity = request, responseType = Cheat::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(game: String, uuid: String, request: ChangeCheatRequest): Cheat {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(game = game, uuid = uuid), entity = request, responseType = Cheat::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(game: String, uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(game = game, uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    /**
     * Returns URL for game's UUID.
     *
     * @param game game's UUID
     * @return url for game's UUID
     */
    private fun getUrl(game: String): String {
        return "${getUrl()}/rest/games/${game}/cheats"
    }

    /**
     * Returns URL for game's UUID and cheat's UUID.
     *
     * @param game game's UUID
     * @param uuid cheat's UUID
     * @return url for game's UUID and cheat's UUID
     */
    private fun getUrl(game: String, uuid: String): String {
        return "${getUrl(game = game)}/${uuid}"
    }

}
