package com.github.vhromada.catalog.web.connector.common.error

import com.github.vhromada.catalog.common.result.Result
import com.github.vhromada.catalog.web.connector.common.RestRequest
import org.springframework.web.client.HttpStatusCodeException

/**
 * An interface represents error handler.
 *
 * @author Vladimir Hromada
 */
interface ResponseErrorHandler {

    /**
     * Handles error response.
     *
     * @param system    system
     * @param data      request
     * @param exception HTTP exception
     * @return result with errors
     */
    fun handle(system: String, data: RestRequest<*>, exception: HttpStatusCodeException): Result<Unit>

}
