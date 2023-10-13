package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.entity.ChangeSongRequest
import com.github.vhromada.catalog.web.connector.entity.Song

/**
 * An interface represents connector for songs.
 *
 * @author Vladimir Hromada
 */
interface SongConnector {

    /**
     * Returns list of songs for specified music and filter.
     *
     * @param music music's UUID
     * @return list of songs for specified music and filter
     */
    fun search(music: String, filter: PagingFilter): Page<Song>

    /**
     * Returns song.
     *
     * @param music music's UUID
     * @param uuid  song's UUID
     * @return song
     */
    fun get(music: String, uuid: String): Song

    /**
     * Adds song.
     *
     * @param music   music's UUID
     * @param request request for changing song
     * @return created song
     */
    fun add(music: String, request: ChangeSongRequest): Song

    /**
     * Updates song.
     *
     * @param music   music's UUID
     * @param uuid    song's UUID
     * @param request request for changing song
     * @return updated song
     */
    fun update(music: String, uuid: String, request: ChangeSongRequest): Song

    /**
     * Removes song.
     *
     * @param music music's UUID
     * @param uuid  song's UUID
     */
    fun remove(music: String, uuid: String)

    /**
     * Duplicates song.
     *
     * @param music music's UUID
     * @param uuid  song's UUID
     * @return created duplicated song
     */
    fun duplicate(music: String, uuid: String): Song

}
