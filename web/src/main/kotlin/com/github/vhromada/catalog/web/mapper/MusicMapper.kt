package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeMusicRequest
import com.github.vhromada.catalog.web.connector.entity.Music
import com.github.vhromada.catalog.web.fo.MusicFO

/**
 * An interface represents mapper for music.
 *
 * @author Vladimir Hromada
 */
interface MusicMapper {

    /**
     * Maps music.
     *
     * @param source music
     * @return mapped FO for music
     */
    fun map(source: Music): MusicFO

    /**
     * Maps FO for music.
     *
     * @param source FO for music
     * @return mapped request for changing music
     */
    fun mapRequest(source: MusicFO): ChangeMusicRequest

}
