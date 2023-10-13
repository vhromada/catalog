package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.ChangeMusicRequest
import com.github.vhromada.catalog.web.connector.entity.Music
import com.github.vhromada.catalog.web.connector.entity.MusicStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter

/**
 * An interface represents connector for music.
 *
 * @author Vladimir Hromada
 */
interface MusicConnector {

    /**
     * Returns list of music for filter.
     *
     * @param filter filter
     * @return list of music for filter
     */
    fun search(filter: NameFilter): Page<Music>

    /**
     * Returns music.
     *
     * @param uuid UUID
     * @return music
     */
    fun get(uuid: String): Music

    /**
     * Adds music.
     *
     * @param request request for changing music
     * @return created music
     */
    fun add(request: ChangeMusicRequest): Music

    /**
     * Updates music.
     *
     * @param uuid    UUID
     * @param request request for changing music
     * @return updated music
     */
    fun update(uuid: String, request: ChangeMusicRequest): Music

    /**
     * Removes music.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates music.
     *
     * @param uuid UUID
     * @return created duplicated music
     */
    fun duplicate(uuid: String): Music

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): MusicStatistics

}
