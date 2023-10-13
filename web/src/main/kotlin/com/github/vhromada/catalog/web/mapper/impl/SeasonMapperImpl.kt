package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeSeasonRequest
import com.github.vhromada.catalog.web.connector.entity.Season
import com.github.vhromada.catalog.web.fo.SeasonFO
import com.github.vhromada.catalog.web.mapper.SeasonMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for seasons.
 *
 * @author Vladimir Hromada
 */
@Component("seasonMapper")
class SeasonMapperImpl : SeasonMapper {

    override fun map(source: Season): SeasonFO {
        return SeasonFO(
            uuid = source.uuid,
            number = source.number.toString(),
            startYear = source.startYear.toString(),
            endYear = source.endYear.toString(),
            language = source.language,
            subtitles = source.subtitles,
            note = source.note
        )
    }

    override fun mapRequest(source: SeasonFO): ChangeSeasonRequest {
        return ChangeSeasonRequest(
            number = source.number!!.toInt(),
            startYear = source.startYear!!.toInt(),
            endYear = source.endYear!!.toInt(),
            language = source.language!!,
            subtitles = source.subtitles!!,
            note = source.note
        )
    }

}
