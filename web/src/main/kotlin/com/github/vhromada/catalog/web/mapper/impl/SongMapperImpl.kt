package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeSongRequest
import com.github.vhromada.catalog.web.connector.entity.Song
import com.github.vhromada.catalog.web.fo.SongFO
import com.github.vhromada.catalog.web.mapper.SongMapper
import com.github.vhromada.catalog.web.mapper.TimeMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for songs.
 *
 * @author Vladimir Hromada
 */
@Component("songMapper")
class SongMapperImpl(
    /**
     * Mapper for time
     */
    private val mapper: TimeMapper
) : SongMapper {

    override fun map(source: Song): SongFO {
        return SongFO(
            uuid = source.uuid,
            name = source.name,
            length = mapper.map(source = source.length),
            note = source.note
        )
    }

    override fun mapRequest(source: SongFO): ChangeSongRequest {
        return ChangeSongRequest(
            name = source.name!!,
            length = mapper.mapBack(source = source.length!!),
            note = source.note
        )
    }

}
