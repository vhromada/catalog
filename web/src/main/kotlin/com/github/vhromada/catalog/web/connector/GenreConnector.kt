package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.ChangeGenreRequest
import com.github.vhromada.catalog.web.connector.entity.Genre
import com.github.vhromada.catalog.web.connector.entity.GenreStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter

/**
 * An interface represents connector for genres.
 *
 * @author Vladimir Hromada
 */
interface GenreConnector {

    /**
     * Returns list of genres.
     *
     * @return list of genres
     */
    fun getAll(): List<Genre>

    /**
     * Returns list of genres for filter.
     *
     * @param filter filter
     * @return list of genres for filter
     */
    fun search(filter: NameFilter): Page<Genre>

    /**
     * Returns genre.
     *
     * @param uuid UUID
     * @return genre
     */
    fun get(uuid: String): Genre

    /**
     * Adds genre.
     *
     * @param request request for changing genre
     * @return created genre
     */
    fun add(request: ChangeGenreRequest): Genre

    /**
     * Updates genre.
     *
     * @param uuid    UUID
     * @param request request for changing genre
     * @return updated genre
     */
    fun update(uuid: String, request: ChangeGenreRequest): Genre

    /**
     * Removes genre.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates genre.
     *
     * @param uuid UUID
     * @return created duplicated genre
     */
    fun duplicate(uuid: String): Genre

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): GenreStatistics

}
