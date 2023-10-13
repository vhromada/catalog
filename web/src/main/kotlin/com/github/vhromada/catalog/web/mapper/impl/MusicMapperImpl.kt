package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeMusicRequest
import com.github.vhromada.catalog.web.connector.entity.Music
import com.github.vhromada.catalog.web.fo.MusicFO
import com.github.vhromada.catalog.web.mapper.MusicMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for music.
 *
 * @author Vladimir Hromada
 */
@Component("musicMapper")
class MusicMapperImpl : MusicMapper {

    override fun map(source: Music): MusicFO {
        return MusicFO(
            uuid = source.uuid,
            name = source.name,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            mediaCount = source.mediaCount.toString(),
            note = source.note
        )
    }

    override fun mapRequest(source: MusicFO): ChangeMusicRequest {
        return ChangeMusicRequest(
            name = source.name!!,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            mediaCount = source.mediaCount!!.toInt(),
            note = source.note
        )
    }

}
