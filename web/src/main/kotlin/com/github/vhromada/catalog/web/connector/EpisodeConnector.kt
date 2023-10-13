package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.entity.ChangeEpisodeRequest
import com.github.vhromada.catalog.web.connector.entity.Episode

/**
 * An interface represents connector for episodes.
 *
 * @author Vladimir Hromada
 */
interface EpisodeConnector {

    /**
     * Returns list of episodes for specified season and filter.
     *
     * @param show   show's UUID
     * @param season season's UUID
     * @return list of episodes for specified season and filter
     */
    fun search(show: String, season: String, filter: PagingFilter): Page<Episode>

    /**
     * Returns episode.
     *
     * @param show   show's UUID
     * @param season season's UUID
     * @param uuid   episode's UUID
     * @return episode
     */
    fun get(show: String, season: String, uuid: String): Episode

    /**
     * Adds episode.
     *
     * @param show    show's UUID
     * @param season  season's UUID
     * @param request request for changing episode
     * @return created episode
     */
    fun add(show: String, season: String, request: ChangeEpisodeRequest): Episode

    /**
     * Updates episode.
     *
     * @param show    show's UUID
     * @param season  season's UUID
     * @param uuid    episode's UUID
     * @param request request for changing episode
     * @return updated episode
     */
    fun update(show: String, season: String, uuid: String, request: ChangeEpisodeRequest): Episode

    /**
     * Removes episode.
     *
     * @param show   show's UUID
     * @param season season's UUID
     * @param uuid   episode's UUID
     */
    fun remove(show: String, season: String, uuid: String)

    /**
     * Duplicates episode.
     *
     * @param show   show's UUID
     * @param season season's UUID
     * @param uuid   episode's UUID
     * @return created duplicated episode
     */
    fun duplicate(show: String, season: String, uuid: String): Episode

}
