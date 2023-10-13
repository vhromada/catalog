package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.entity.BookItem
import com.github.vhromada.catalog.web.connector.entity.ChangeBookItemRequest

/**
 * An interface represents connector for book items.
 *
 * @author Vladimir Hromada
 */
interface BookItemConnector {

    /**
     * Returns list of book items for specified book and filter.
     *
     * @param book book's UUID
     * @return list of book items for specified book and filter
     */
    fun search(book: String, filter: PagingFilter): Page<BookItem>

    /**
     * Returns book item.
     *
     * @param book book's UUID
     * @param uuid book item's UUID
     * @return book item
     */
    fun get(book: String, uuid: String): BookItem

    /**
     * Adds book item.
     *
     * @param book    book's UUID
     * @param request request for changing boo item
     * @return created book item
     */
    fun add(book: String, request: ChangeBookItemRequest): BookItem

    /**
     * Updates book item.
     *
     * @param book    book's UUID
     * @param uuid    book item's UUID
     * @param request request for changing book item
     * @return updated book item
     */
    fun update(book: String, uuid: String, request: ChangeBookItemRequest): BookItem

    /**
     * Removes book item.
     *
     * @param book book's UUID
     * @param uuid book item's UUID
     */
    fun remove(book: String, uuid: String)

    /**
     * Duplicates book item.
     *
     * @param book book's UUID
     * @param uuid book item's UUID
     * @return created duplicated book item
     */
    fun duplicate(book: String, uuid: String): BookItem

}
