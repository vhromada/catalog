package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeCheatRequest
import com.github.vhromada.catalog.web.connector.entity.Cheat
import com.github.vhromada.catalog.web.fo.CheatFO
import com.github.vhromada.catalog.web.mapper.CheatDataMapper
import com.github.vhromada.catalog.web.mapper.CheatMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for cheats.
 *
 * @author Vladimir Hromada
 */
@Component("cheatMapper")
class CheatMapperImpl(
    /**
     * Mapper for cheat's data
     */
    private val mapper: CheatDataMapper
) : CheatMapper {

    override fun map(source: Cheat): CheatFO {
        return CheatFO(
            uuid = source.uuid,
            gameSetting = source.gameSetting,
            cheatSetting = source.cheatSetting,
            data = mapper.mapList(source = source.data)
        )
    }

    override fun mapRequest(source: CheatFO): ChangeCheatRequest {
        return ChangeCheatRequest(
            gameSetting = source.gameSetting,
            cheatSetting = source.cheatSetting,
            data = mapper.mapRequests(source = source.data!!.filterNotNull())
        )
    }

}
