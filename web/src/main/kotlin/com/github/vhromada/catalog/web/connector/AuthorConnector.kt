package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.Author
import com.github.vhromada.catalog.web.connector.entity.AuthorStatistics
import com.github.vhromada.catalog.web.connector.entity.ChangeAuthorRequest
import com.github.vhromada.catalog.web.connector.filter.AuthorFilter

/**
 * An interface represents connector for authors.
 *
 * @author Vladimir Hromada
 */
interface AuthorConnector {

    /**
     * Returns list of authors.
     *
     * @return list of authors
     */
    fun getAll(): List<Author>

    /**
     * Returns list of authors for filter.
     *
     * @param filter filter
     * @return list of authors for filter
     */
    fun search(filter: AuthorFilter): Page<Author>

    /**
     * Returns author.
     *
     * @param uuid UUID
     * @return author
     */
    fun get(uuid: String): Author

    /**
     * Adds author.
     *
     * @param request request for changing author
     * @return created author
     */
    fun add(request: ChangeAuthorRequest): Author

    /**
     * Updates author.
     *
     * @param uuid    UUID
     * @param request request for changing author
     * @return updated author
     */
    fun update(uuid: String, request: ChangeAuthorRequest): Author

    /**
     * Removes author.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates author.
     *
     * @param uuid UUID
     * @return created duplicated author
     */
    fun duplicate(uuid: String): Author

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): AuthorStatistics

}
