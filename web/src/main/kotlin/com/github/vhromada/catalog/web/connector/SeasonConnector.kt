package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.entity.ChangeSeasonRequest
import com.github.vhromada.catalog.web.connector.entity.Season

/**
 * An interface represents connector for seasons.
 *
 * @author Vladimir Hromada
 */
interface SeasonConnector {

    /**
     * Returns list of seasons for specified show and filter.
     *
     * @param show show's UUID
     * @return list of seasons for specified show and filter
     */
    fun search(show: String, filter: PagingFilter): Page<Season>

    /**
     * Returns season.
     *
     * @param show show's UUID
     * @param uuid season's UUID
     * @return season
     */
    fun get(show: String, uuid: String): Season

    /**
     * Adds season.
     *
     * @param show    show's UUID
     * @param request request for changing season
     * @return created season
     */
    fun add(show: String, request: ChangeSeasonRequest): Season

    /**
     * Updates season.
     *
     * @param show    show's UUID
     * @param uuid    season's UUID
     * @param request request for changing season
     * @return updated season
     */
    fun update(show: String, uuid: String, request: ChangeSeasonRequest): Season

    /**
     * Removes season.
     *
     * @param show show's UUID
     * @param uuid season's UUID
     */
    fun remove(show: String, uuid: String)

    /**
     * Duplicates season.
     *
     * @param show show's UUID
     * @param uuid season's UUID
     * @return created duplicated season
     */
    fun duplicate(show: String, uuid: String): Season

}
