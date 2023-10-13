package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeShowRequest
import com.github.vhromada.catalog.web.connector.entity.Show
import com.github.vhromada.catalog.web.fo.ShowFO

/**
 * An interface represents mapper for shows.
 *
 * @author Vladimir Hromada
 */
interface ShowMapper {

    /**
     * Maps show.
     *
     * @param source show
     * @return mapped FO for show
     */
    fun map(source: Show): ShowFO

    /**
     * Maps FO for show.
     *
     * @param source FO for show
     * @return mapped request for changing show
     */
    fun mapRequest(source: ShowFO): ChangeShowRequest

}
