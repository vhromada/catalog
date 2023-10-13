package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.fo.TimeFO

/**
 * An interface represents mapper for time.
 *
 * @author Vladimir Hromada
 */
interface TimeMapper {

    /**
     * Maps time.
     *
     * @param source time
     * @return mapped FO for time
     */
    fun map(source: Int): TimeFO

    /**
     * Maps FO for time.
     *
     * @param source FO for time
     * @return mapped time
     */
    fun mapBack(source: TimeFO): Int

}
