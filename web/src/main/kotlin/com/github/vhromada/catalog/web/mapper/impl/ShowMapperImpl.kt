package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeShowRequest
import com.github.vhromada.catalog.web.connector.entity.Show
import com.github.vhromada.catalog.web.fo.ShowFO
import com.github.vhromada.catalog.web.mapper.ShowMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for shows.
 *
 * @author Vladimir Hromada
 */
@Component("showMapper")
class ShowMapperImpl : ShowMapper {

    override fun map(source: Show): ShowFO {
        return ShowFO(
            uuid = source.uuid,
            czechName = source.czechName,
            originalName = source.originalName,
            csfd = source.csfd,
            imdb = source.imdbCode != null && source.imdbCode >= 1,
            imdbCode = if (source.imdbCode == null || source.imdbCode < 1) null else source.imdbCode.toString(),
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            picture = source.picture,
            note = source.note,
            genres = source.genres.map { it.uuid }
        )
    }

    override fun mapRequest(source: ShowFO): ChangeShowRequest {
        return ChangeShowRequest(
            czechName = source.czechName!!,
            originalName = source.originalName!!,
            csfd = source.csfd,
            imdbCode = if (source.imdb) source.imdbCode!!.toInt() else -1,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            picture = source.picture,
            note = source.note,
            genres = source.genres!!.filterNotNull()
        )
    }

}
