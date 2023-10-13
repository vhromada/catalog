package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.ChangeShowRequest
import com.github.vhromada.catalog.web.connector.entity.Show
import com.github.vhromada.catalog.web.connector.entity.ShowStatistics
import com.github.vhromada.catalog.web.connector.filter.MultipleNameFilter

/**
 * An interface represents connector for shows.
 *
 * @author Vladimir Hromada
 */
interface ShowConnector {

    /**
     * Returns list of shows for filter.
     *
     * @param filter filter
     * @return list of shows for filter
     */
    fun search(filter: MultipleNameFilter): Page<Show>

    /**
     * Returns show.
     *
     * @param uuid UUID
     * @return show
     */
    fun get(uuid: String): Show

    /**
     * Adds show.
     *
     * @param request request for changing show
     * @return created show
     */
    fun add(request: ChangeShowRequest): Show

    /**
     * Updates show.
     *
     * @param uuid    UUID
     * @param request request for changing show
     * @return updated show
     */
    fun update(uuid: String, request: ChangeShowRequest): Show

    /**
     * Removes show.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates show.
     *
     * @param uuid UUID
     * @return created duplicated show
     */
    fun duplicate(uuid: String): Show

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): ShowStatistics

}
