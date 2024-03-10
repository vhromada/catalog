package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.web.connector.RegisterConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

/**
 * A class represents implementation of connector for registers.
 *
 * @author Vladimir Hromada
 */
@Component("registerConnector")
class RegisterConnectorImpl(
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
), RegisterConnector {

    override fun getProgramFormats(): List<String> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/formats/programs", parameterizedType = object : ParameterizedTypeReference<List<String>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun getBookItemFormats(): List<String> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/formats/book-items", parameterizedType = object : ParameterizedTypeReference<List<String>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun getLanguages(): List<String> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/languages", parameterizedType = object : ParameterizedTypeReference<List<String>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun getSubtitles(): List<String> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/subtitles", parameterizedType = object : ParameterizedTypeReference<List<String>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun getUrl(): String {
        return "${super.getUrl()}/rest/registers"
    }

}
