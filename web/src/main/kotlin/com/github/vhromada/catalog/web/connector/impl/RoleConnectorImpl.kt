package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.web.connector.RoleConnector
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
 * A class represents implementation of connector for roles.
 *
 * @author Vladimir Hromada
 */
@Component("roleConnector")
class RoleConnectorImpl(
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
), RoleConnector {

    override fun getRoles(): List<String> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/rest/roles", parameterizedType = object : ParameterizedTypeReference<List<String>>() {}))
            .throwExceptionIfAny()
            .get()
    }

}
