package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.ChangeMovieRequest
import com.github.vhromada.catalog.web.connector.entity.Movie
import com.github.vhromada.catalog.web.connector.entity.MovieStatistics
import com.github.vhromada.catalog.web.connector.filter.MultipleNameFilter

/**
 * An interface represents connector for movies.
 *
 * @author Vladimir Hromada
 */
interface MovieConnector {

    /**
     * Returns list of movies for filter.
     *
     * @param filter filter
     * @return list of movies for filter
     */
    fun search(filter: MultipleNameFilter): Page<Movie>

    /**
     * Returns movie.
     *
     * @param uuid UUID
     * @return movie
     */
    fun get(uuid: String): Movie

    /**
     * Adds movie.
     *
     * @param request request for changing movie
     * @return created movie
     */
    fun add(request: ChangeMovieRequest): Movie

    /**
     * Updates movie.
     *
     * @param uuid    UUID
     * @param request request for changing movie
     * @return updated movie
     */
    fun update(uuid: String, request: ChangeMovieRequest): Movie

    /**
     * Removes movie.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates movie.
     *
     * @param uuid UUID
     * @return created duplicated movie
     */
    fun duplicate(uuid: String): Movie

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): MovieStatistics

}
