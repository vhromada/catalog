package com.github.vhromada.catalog.web.connector.common

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod

/**
 * A class represents request for REST.
 *
 * @param T type of receiving data
 * @author Vladimir Hromada
 */
class RestRequest<T>(
    /**
     * HTTP method
     */
    val method: HttpMethod,

    /**
     * URL
     */
    val url: String,

    /**
     * Entity
     */
    private val entity: Any? = null,

    /**
     * Response type
     */
    val responseType: Class<T>? = null,

    /**
     * Parameterized response type
     */
    val parameterizedType: ParameterizedTypeReference<T>? = null,

    /**
     * Parameters
     */
    val params: Map<String, Any> = emptyMap()
) {

    init {
        require((parameterizedType != null && responseType == null) || (parameterizedType == null && responseType != null)) { "Only one of attribute parameterizedType or responseType is required" }
    }

    /**
     * Returns HTTP entity.
     *
     * @return HTTP entity
     */
    fun httpEntity(): HttpEntity<*> {
        return when (entity) {
            null -> HttpEntity<Any>(null, null)
            is HttpEntity<*> -> entity
            else -> HttpEntity<Any>(entity)
        }
    }

}
