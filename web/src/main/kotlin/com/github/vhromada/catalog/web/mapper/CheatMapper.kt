package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeCheatRequest
import com.github.vhromada.catalog.web.connector.entity.Cheat
import com.github.vhromada.catalog.web.fo.CheatFO

/**
 * An interface represents mapper for cheats.
 *
 * @author Vladimir Hromada
 */
interface CheatMapper {

    /**
     * Maps cheat.
     *
     * @param source cheat
     * @return mapped FO for cheat
     */
    fun map(source: Cheat): CheatFO

    /**
     * Maps FO for cheat.
     *
     * @param source FO for cheat
     * @return mapped request for changing cheat
     */
    fun mapRequest(source: CheatFO): ChangeCheatRequest

}
