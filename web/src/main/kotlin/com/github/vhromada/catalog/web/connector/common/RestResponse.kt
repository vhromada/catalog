package com.github.vhromada.catalog.web.connector.common

import com.github.vhromada.catalog.common.exception.InputException
import com.github.vhromada.catalog.common.result.Result
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpStatusCodeException

/**
 * A class represents response for REST.
 *
 * @param T type of data
 * @author Vladimir Hromada
 */
class RestResponse<T>(
    /**
     * HTTP status
     */
    val httpStatus: HttpStatusCode,

    /**
     * Response entity
     */
    val responseEntity: ResponseEntity<T?>? = null,

    /**
     * Exception
     */
    val exception: HttpStatusCodeException? = null,

    /**
     * Result
     */
    val result: Result<*>? = null
) {

    /**
     * Returns response body.
     *
     * @return response body
     */
    fun get(): T {
        return responseEntity!!.body!!
    }

    /**
     * Throws exception if exists.
     *
     * @return response
     * @throws InputException if exists
     */
    fun throwExceptionIfAny(): RestResponse<T> {
        if (exception != null) {
            throw InputException(result = result!!, httpStatus = httpStatus)
        }
        return this
    }

}
