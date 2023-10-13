package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeGameRequest
import com.github.vhromada.catalog.web.connector.entity.Game
import com.github.vhromada.catalog.web.fo.GameFO
import com.github.vhromada.catalog.web.mapper.GameMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for games.
 *
 * @author Vladimir Hromada
 */
@Component("gameMapper")
class GameMapperImpl : GameMapper {

    override fun map(source: Game): GameFO {
        return GameFO(
            uuid = source.uuid,
            name = source.name,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            mediaCount = source.mediaCount.toString(),
            format = source.format,
            crack = source.crack,
            serialKey = source.serialKey,
            patch = source.patch,
            trainer = source.trainer,
            trainerData = source.trainerData,
            editor = source.editor,
            saves = source.saves,
            otherData = source.otherData,
            note = source.note
        )
    }

    override fun mapRequest(source: GameFO): ChangeGameRequest {
        return ChangeGameRequest(
            name = source.name!!,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            mediaCount = source.mediaCount!!.toInt(),
            format = source.format!!,
            crack = source.crack!!,
            serialKey = source.serialKey!!,
            patch = source.patch!!,
            trainer = source.trainer!!,
            trainerData = source.trainerData!!,
            editor = source.editor!!,
            saves = source.saves!!,
            otherData = source.otherData,
            note = source.note
        )
    }

}
