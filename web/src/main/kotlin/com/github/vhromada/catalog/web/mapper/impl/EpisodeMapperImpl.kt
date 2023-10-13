package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeEpisodeRequest
import com.github.vhromada.catalog.web.connector.entity.Episode
import com.github.vhromada.catalog.web.fo.EpisodeFO
import com.github.vhromada.catalog.web.mapper.EpisodeMapper
import com.github.vhromada.catalog.web.mapper.TimeMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for episodes.
 *
 * @author Vladimir Hromada
 */
@Component("episodeMapper")
class EpisodeMapperImpl(
    /**
     * Mapper for time
     */
    private val mapper: TimeMapper
) : EpisodeMapper {

    override fun map(source: Episode): EpisodeFO {
        return EpisodeFO(
            uuid = source.uuid,
            number = source.number.toString(),
            length = mapper.map(source = source.length),
            name = source.name,
            note = source.note
        )
    }

    override fun mapRequest(source: EpisodeFO): ChangeEpisodeRequest {
        return ChangeEpisodeRequest(
            number = source.number!!.toInt(),
            length = mapper.mapBack(source = source.length!!),
            name = source.name!!,
            note = source.note,
        )
    }

}
