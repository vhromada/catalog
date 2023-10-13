package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeEpisodeRequest
import com.github.vhromada.catalog.web.connector.entity.Episode
import com.github.vhromada.catalog.web.fo.EpisodeFO

/**
 * An interface represents mapper for episodes.
 *
 * @author Vladimir Hromada
 */
interface EpisodeMapper {

    /**
     * Maps episode.
     *
     * @param source episode
     * @return mapped FO for episode
     */
    fun map(source: Episode): EpisodeFO

    /**
     * Maps FO for episode.
     *
     * @param source FO for episode
     * @return mapped request for changing episode
     */
    fun mapRequest(source: EpisodeFO): ChangeEpisodeRequest

}
