package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeGameRequest
import com.github.vhromada.catalog.web.connector.entity.Game
import com.github.vhromada.catalog.web.fo.GameFO

/**
 * An interface represents mapper for games.
 *
 * @author Vladimir Hromada
 */
interface GameMapper {

    /**
     * Maps game.
     *
     * @param source game
     * @return mapped FO for game
     */
    fun map(source: Game): GameFO

    /**
     * Maps FO for game.
     *
     * @param source FO for game
     * @return mapped request for changing game
     */
    fun mapRequest(source: GameFO): ChangeGameRequest

}
