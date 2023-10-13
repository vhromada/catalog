package com.github.vhromada.catalog.web.connector.common

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.UUID

/**
 * A class represents interceptor for logging.
 *
 * @author Vladimir Hromada
 */
@Component("restLoggingInterceptor")
class RestLoggingInterceptor(
    /**
     * True if logging is enabled
     */
    @Value("\${catalog.connector.log.enabled:false}") private val logEnabled: Boolean
) : ClientHttpRequestInterceptor {

    /**
     * Logger
     */
    private val logger = KotlinLogging.logger {}

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val id = UUID.randomUUID().toString()
        logRequest(id = id, request = request, content = body)
        val response = execution.execute(request, body)
        logResponse(id = id, request = request, response = response)
        return response
    }

    /**
     * Logs request.
     *
     * @param id      ID of call
     * @param request request
     * @param content content of request
     */
    private fun logRequest(id: String, request: HttpRequest, content: ByteArray) {
        if (!logEnabled) {
            return
        }
        val message = StringBuilder("Req[$id] ${request.method} uri=${request.uri} headers=${request.headers}")
        if (content.isNotEmpty()) {
            val payload = String(content, StandardCharsets.UTF_8)
            message.append(" payload=[$payload]")
        }
        logger.info { message }
    }

    /**
     * Logs response.
     *
     * @param id       ID of call
     * @param request  request
     * @param response response
     */
    private fun logResponse(id: String?, request: HttpRequest, response: ClientHttpResponse) {
        if (!logEnabled) {
            return
        }
        val body = getBody(response = response)
        val status = getStatus(response = response)
        logger.info { "Res[$id] ${request.method} uri=${request.uri} headers=${request.headers} payload=[$body] status=$status" }
    }

    /**
     * Returns body of response.
     *
     * @param response response
     * @return body of response
     */
    private fun getBody(response: ClientHttpResponse): String {
        return try {
            String(IOUtils.toByteArray(response.body), StandardCharsets.UTF_8).ifEmpty { "" }
        } catch (exception: IOException) {
            "UNREACHABLE"
        }
    }

    /**
     * Returns status.
     *
     * @param response response
     * @return status
     */
    private fun getStatus(response: ClientHttpResponse): String {
        try {
            val status = StringBuilder(response.statusCode.value().toString())
            if (response.statusText.isNotEmpty()) {
                status.append(" ${response.statusText}")
            }
            return status.toString()
        } catch (exception: IOException) {
            throw IllegalStateException("Cannot obtain response status", exception)
        }
    }

}
