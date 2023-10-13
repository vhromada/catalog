package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeSeasonRequest
import com.github.vhromada.catalog.web.connector.entity.Season
import com.github.vhromada.catalog.web.fo.SeasonFO

/**
 * An interface represents mapper for seasons.
 *
 * @author Vladimir Hromada
 */
interface SeasonMapper {

    /**
     * Maps season.
     *
     * @param source season
     * @return mapped FO for season
     */
    fun map(source: Season): SeasonFO

    /**
     * Maps FO for season.
     *
     * @param source FO for season
     * @return mapped request for changing season
     */
    fun mapRequest(source: SeasonFO): ChangeSeasonRequest

}
