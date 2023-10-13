package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeCheatData
import com.github.vhromada.catalog.web.connector.entity.CheatData
import com.github.vhromada.catalog.web.fo.CheatDataFO

/**
 * An interface represents mapper for cheat's data.
 *
 * @author Vladimir Hromada
 */
interface CheatDataMapper {

    /**
     * Maps cheat's data.
     *
     * @param source cheat's data
     * @return mapped FO for cheat's data
     */
    fun map(source: CheatData): CheatDataFO

    /**
     * Maps list of cheat's data.
     *
     * @param source list of cheat's data
     * @return mapped list of FO for cheat's data
     */
    fun mapList(source: List<CheatData>): List<CheatDataFO>

    /**
     * Maps FO for cheat's data.
     *
     * @param source FO for cheat's data
     * @return mapped changing cheat's data
     */
    fun mapRequest(source: CheatDataFO): ChangeCheatData

    /**
     * Maps list of FO for cheat's data.
     *
     * @param source list of FO for cheat's data
     * @return mapped list of changing cheat's data
     */
    fun mapRequests(source: List<CheatDataFO>): List<ChangeCheatData>

}
