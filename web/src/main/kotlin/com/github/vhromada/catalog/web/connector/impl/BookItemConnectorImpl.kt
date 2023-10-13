package com.github.vhromada.catalog.web.connector.impl

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.BookItemConnector
import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import com.github.vhromada.catalog.web.connector.common.RestConnector
import com.github.vhromada.catalog.web.connector.common.RestLoggingInterceptor
import com.github.vhromada.catalog.web.connector.common.RestRequest
import com.github.vhromada.catalog.web.connector.common.UserInterceptor
import com.github.vhromada.catalog.web.connector.common.error.ResponseErrorHandler
import com.github.vhromada.catalog.web.connector.entity.BookItem
import com.github.vhromada.catalog.web.connector.entity.ChangeBookItemRequest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents implementation of connector for book items.
 *
 * @author Vladimir Hromada
 */
@Component("bookItemConnector")
class BookItemConnectorImpl(
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
), BookItemConnector {

    override fun search(book: String, filter: PagingFilter): Page<BookItem> {
        val url = UriComponentsBuilder.fromUriString(getUrl(book = book))
        filter.createUrl(builder = url)
        return exchange(request = RestRequest(method = HttpMethod.GET, url = url.build().toUriString(), parameterizedType = object : ParameterizedTypeReference<Page<BookItem>>() {}))
            .throwExceptionIfAny()
            .get()
    }

    override fun get(book: String, uuid: String): BookItem {
        return exchange(request = RestRequest(method = HttpMethod.GET, url = getUrl(book = book, uuid = uuid), responseType = BookItem::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun add(book: String, request: ChangeBookItemRequest): BookItem {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = getUrl(book = book), entity = request, responseType = BookItem::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun update(book: String, uuid: String, request: ChangeBookItemRequest): BookItem {
        return exchange(request = RestRequest(method = HttpMethod.PUT, url = getUrl(book = book, uuid = uuid), entity = request, responseType = BookItem::class.java))
            .throwExceptionIfAny()
            .get()
    }

    override fun remove(book: String, uuid: String) {
        exchange(request = RestRequest(method = HttpMethod.DELETE, url = getUrl(book = book, uuid = uuid), responseType = Unit::class.java))
            .throwExceptionIfAny()
    }

    override fun duplicate(book: String, uuid: String): BookItem {
        return exchange(request = RestRequest(method = HttpMethod.POST, url = "${getUrl(book = book, uuid = uuid)}/duplicate", responseType = BookItem::class.java))
            .throwExceptionIfAny()
            .get()
    }

    /**
     * Returns URL for book's UUID.
     *
     * @param book book's UUID
     * @return URL for book's UUID
     */
    private fun getUrl(book: String): String {
        return "${getUrl()}/rest/books/${book}/items"
    }

    /**
     * Returns URL for book's UUID and book item's UUID.
     *
     * @param book book's UUID
     * @param uuid book item's UUID
     * @return URL for book's UUID and book item's UUID
     */
    private fun getUrl(book: String, uuid: String): String {
        return "${getUrl(book = book)}/${uuid}"
    }

}
