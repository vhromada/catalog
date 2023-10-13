package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.Book
import com.github.vhromada.catalog.web.connector.entity.BookStatistics
import com.github.vhromada.catalog.web.connector.entity.ChangeBookRequest
import com.github.vhromada.catalog.web.connector.filter.MultipleNameFilter

/**
 * An interface represents connector for books.
 *
 * @author Vladimir Hromada
 */
interface BookConnector {

    /**
     * Returns list of books for filter.
     *
     * @param filter filter
     * @return list of books for filter
     */
    fun search(filter: MultipleNameFilter): Page<Book>

    /**
     * Returns book.
     *
     * @param uuid UUID
     * @return book
     */
    fun get(uuid: String): Book

    /**
     * Adds book.
     *
     * @param request request for changing book
     * @return created book
     */
    fun add(request: ChangeBookRequest): Book

    /**
     * Updates book.
     *
     * @param uuid    UUID
     * @param request request for changing book
     * @return updated book
     */
    fun update(uuid: String, request: ChangeBookRequest): Book

    /**
     * Removes book.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates book.
     *
     * @param uuid UUID
     * @return created duplicated book
     */
    fun duplicate(uuid: String): Book

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): BookStatistics

}
