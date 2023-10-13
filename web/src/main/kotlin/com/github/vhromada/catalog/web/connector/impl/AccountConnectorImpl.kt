package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.AccountConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.RestResponse
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.Account
import com.github.vhromada.catalog.web.connector.entity.AccountStatistics
import com.github.vhromada.catalog.web.connector.entity.ChangeRolesRequest
import com.github.vhromada.catalog.web.connector.entity.Credentials
import com.github.vhromada.catalog.web.connector.filter.AccountFilter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for accounts.
 *
 * @author Vladimir Hromada
 */
@Component("accountConnector")
class AccountConnectorImpl(
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
), AccountConnector {

    override fun search(filter: AccountFilter): Page<Account> {
        val url = UriComponentsBuilder.fromUriString(getUrl())
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<Account>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(uuid: String): Account {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(uuid = uuid), responseType = Account::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun updateCredentials(uuid: String, credentials: Credentials): Account {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = "${getUrl(uuid = uuid)}/credentials", entity = credentials, responseType = Account::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun updateRoles(uuid: String, request: ChangeRolesRequest): Account {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = "${getUrl(uuid = uuid)}/roles", entity = request, responseType = Account::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun getStatistics(): AccountStatistics {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/statistics", responseType = AccountStatistics::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun addCredentials(credentials: Credentials): Account {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl()}/credentials", entity = credentials, responseType = Account::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun checkCredentials(credentials: Credentials): RestResponse<Account> {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl()}/credentials/check", entity = credentials, responseType = Account::class.java))
    }

    override fun getUrl(): String {
        return "${super.getUrl()}/rest/accounts"
    }

    /**
     * Returns URL for UUID.
     *
     * @param uuid UUID
     * @return url for UUID
     */
    fun getUrl(uuid: String): String {
        return "${getUrl()}/${uuid}"
    }

}
