package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.PictureConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.RestResponse
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for pictures.
 *
 * @author Vladimir Hromada
 */
@Component("pictureConnector")
class PictureConnectorImpl(
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
), PictureConnector {

    override fun getAll(): List<String> {
        val filter = PagingFilter()
        filter.page = 1
        filter.limit = Int.MAX_VALUE
        return search(filter = filter).data
    }

    override fun search(filter: PagingFilter): Page<String> {
        val url = UriComponentsBuilder.fromUriString(getUrl())
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<String>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(uuid: String): RestResponse<Resource> {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = "${getUrl()}/${uuid}", responseType = Resource::class.java))
    }

    override fun add(picture: MultipartFile) {
        val parts = LinkedMultiValueMap<String, Any>()
        parts.add("file", object : ByteArrayResource(picture.bytes) {
            override fun getFilename(): String? {
                return picture.originalFilename
            }
        })
        exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(), entity = HttpEntity(parts), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun remove(uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = "${getUrl()}/${uuid}", responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun getUrl(): String {
        return super.getUrl() + "/rest/pictures"
    }

}
