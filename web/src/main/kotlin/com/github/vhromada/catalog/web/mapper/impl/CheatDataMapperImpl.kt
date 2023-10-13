package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeCheatData
import com.github.vhromada.catalog.web.connector.entity.CheatData
import com.github.vhromada.catalog.web.fo.CheatDataFO
import com.github.vhromada.catalog.web.mapper.CheatDataMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for cheats.
 *
 * @author Vladimir Hromada
 */
@Component("cheatDataMapper")
class CheatDataMapperImpl : CheatDataMapper {

    override fun map(source: CheatData): CheatDataFO {
        return CheatDataFO(
            action = source.action,
            description = source.description
        )
    }

    override fun mapList(source: List<CheatData>): List<CheatDataFO> {
        return source.map { map(source = it) }
    }

    override fun mapRequest(source: CheatDataFO): ChangeCheatData {
        return ChangeCheatData(
            action = source.action!!,
            description = source.description!!
        )
    }

    override fun mapRequests(source: List<CheatDataFO>): List<ChangeCheatData> {
        return source.map { mapRequest(source = it) }
    }

}
