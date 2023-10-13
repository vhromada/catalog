package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.ProgramConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.ChangeProgramRequest
import com.github.vhromada.catalog.web.connector.entity.Program
import com.github.vhromada.catalog.web.connector.entity.ProgramStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for programs.
 *
 * @author Vladimir Hromada
 */
@Component("programConnector")
class ProgramConnectorImpl(
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
), ProgramConnector {

    override fun search(filter: NameFilter): Page<Program> {
        val url = UriComponentsBuilder.fromUriString(getUrl())
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Program>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(uuid: String): Program {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/${uuid}", responseType = Program::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(request: ChangeProgramRequest): Program {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(), entity = request, responseType = Program::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(uuid: String, request: ChangeProgramRequest): Program {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = "${getUrl()}/${uuid}", entity = request, responseType = Program::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = "${getUrl()}/${uuid}", responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(uuid: String): Program {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl()}/${uuid}/duplicate", responseType = Program::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getStatistics(): ProgramStatistics {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/statistics", responseType = ProgramStatistics::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getUrl(): String {
        return super.getUrl() + "/rest/programs"
    }

}
