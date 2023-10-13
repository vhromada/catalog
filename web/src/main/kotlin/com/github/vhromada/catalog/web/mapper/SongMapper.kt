package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeSongRequest
import com.github.vhromada.catalog.web.connector.entity.Song
import com.github.vhromada.catalog.web.fo.SongFO

/**
 * An interface represents mapper for songs.
 *
 * @author Vladimir Hromada
 */
interface SongMapper {

    /**
     * Maps song.
     *
     * @param source song
     * @return mapped FO for song
     */
    fun map(source: Song): SongFO

    /**
     * Maps FO for song.
     *
     * @param source FO for song
     * @return mapped request for changing song
     */
    fun mapRequest(source: SongFO): ChangeSongRequest

}
