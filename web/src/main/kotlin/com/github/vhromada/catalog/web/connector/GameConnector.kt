package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.ChangeGameRequest
import com.github.vhromada.catalog.web.connector.entity.Game
import com.github.vhromada.catalog.web.connector.entity.GameStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter

/**
 * An interface represents connector for games.
 *
 * @author Vladimir Hromada
 */
interface GameConnector {

    /**
     * Returns list of games for filter.
     *
     * @param filter filter
     * @return list of games for filter
     */
    fun search(filter: NameFilter): Page<Game>

    /**
     * Returns game.
     *
     * @param uuid UUID
     * @return game
     */
    fun get(uuid: String): Game

    /**
     * Adds game.
     *
     * @param request request for changing game
     * @return created game
     */
    fun add(request: ChangeGameRequest): Game

    /**
     * Updates game.
     *
     * @param uuid    UUID
     * @param request request for changing game
     * @return updated game
     */
    fun update(uuid: String, request: ChangeGameRequest): Game

    /**
     * Removes game.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates game.
     *
     * @param uuid UUID
     * @return created duplicated game
     */
    fun duplicate(uuid: String): Game

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): GameStatistics

}
