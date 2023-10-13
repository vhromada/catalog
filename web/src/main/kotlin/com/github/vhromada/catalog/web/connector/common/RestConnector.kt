package com.github.vhromada.catalog.web.connector.common

import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import org.apache.hc.client5.http.config.ConnectionConfig
import org.apache.hc.client5.http.config.RequestConfig
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder
import org.apache.hc.core5.http.io.SocketConfig
import org.apache.hc.core5.http.ssl.TLS
import org.apache.hc.core5.ssl.SSLContexts
import org.apache.hc.core5.util.TimeValue
import org.apache.hc.core5.util.Timeout
import org.springframework.http.ResponseEntity
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate

/**
 * An abstract class represents connector.
 *
 * @author Vladimir Hromada
 */
abstract class RestConnector(
    /**
     * System
     */
    private val system: String,

    /**
     * Configuration for connector
     */
    private val config: ConnectorConfig,

    /**
     * Error handler
     */
    private val errorHandler: ResponseErrorHandler,

    /**
     * Interceptor for logging
     */
    private val loggingInterceptor: RestLoggingInterceptor,

    /**
     * Interceptor for processing header X-User
     */
    private val userInterceptor: UserInterceptor
) {

    /**
     * Exchanges data with remote server.
     *
     * @param request sending data
     * @return response
     */
    protected fun <T> exchange(request: RestRequest<T>): RestResponse<T> {
        return try {
            val response = call(request = request)
            RestResponse(httpStatus = response.statusCode, responseEntity = response)
        } catch (exception: HttpStatusCodeException) {
            val result = errorHandler.handle(system = system, data = request, exception = exception)
            RestResponse(httpStatus = exception.statusCode, exception = exception, result = result)
        }
    }

    /**
     * Returns url.
     *
     * @return url
     */
    protected open fun getUrl(): String {
        return config.url.get()
    }

    /**
     * Calls remote server.
     *
     * @param request sending data
     * @return response
     */
    private fun <T> call(request: RestRequest<T>): ResponseEntity<T?> {
        val template = RestTemplate(BufferingClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory(getHttpClient())))
        template.interceptors.add(loggingInterceptor)
        template.interceptors.add(userInterceptor)
        return if (request.parameterizedType != null) {
            template.exchange(request.url, request.method, request.httpEntity(), request.parameterizedType, request.params)
        } else {
            template.exchange(request.url, request.method, request.httpEntity(), request.responseType!!, request.params)
        }
    }

    /**
     * Returns HTTP client.
     *
     * @return HTTP client
     */
    private fun getHttpClient(): CloseableHttpClient {
        val sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
            .setSslContext(SSLContexts.createSystemDefault())
            .setTlsVersions(TLS.V_1_3, TLS.V_1_2)
            .build()

        val connectionConfig = ConnectionConfig.custom()
            .setSocketTimeout(Timeout.ofMinutes(1))
            .setConnectTimeout(Timeout.ofMinutes(1))
            .setTimeToLive(TimeValue.ofMinutes(10))
            .build()

        val connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setSSLSocketFactory(sslSocketFactory)
            .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofMinutes(1)).build())
            .setDefaultConnectionConfig(connectionConfig)
            .setMaxConnTotal(100)
            .setMaxConnPerRoute(10)
            .build()

        val requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMinutes(1))
            .setResponseTimeout(Timeout.ofMinutes(1))
            .build()

        return HttpClients.custom()
            .setDefaultCredentialsProvider(config.getCredentialsProvider())
            .setConnectionManager(connectionManager)
            .setDefaultRequestConfig(requestConfig)
            .setRetryStrategy(DefaultHttpRequestRetryStrategy(3, TimeValue.ofSeconds(5)))
            .build()
    }

}
