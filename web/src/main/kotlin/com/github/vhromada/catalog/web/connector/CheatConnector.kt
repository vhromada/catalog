package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.web.connector.common.RestResponse
import com.github.vhromada.catalog.web.connector.entity.ChangeCheatRequest
import com.github.vhromada.catalog.web.connector.entity.Cheat

/**
 * An interface represents connector for cheats.
 *
 * @author Vladimir Hromada
 */
interface CheatConnector {

    /**
     * Returns cheat by game's UUID.
     *
     * @param game game's UUID
     * @return cheat by game's UUID
     */
    fun find(game: String): RestResponse<Cheat>

    /**
     * Returns cheat.
     *
     * @param game game's UUID
     * @param uuid UUID
     * @return cheat
     */
    fun get(game: String, uuid: String): Cheat

    /**
     * Adds cheat.
     *
     * @param game    game's UUID
     * @param request request for changing cheat
     * @return created cheat
     */
    fun add(game: String, request: ChangeCheatRequest): Cheat

    /**
     * Updates cheat.
     *
     * @param game    game's UUID
     * @param uuid    UUID
     * @param request request for changing cheat
     * @return updated cheat
     */
    fun update(game: String, uuid: String, request: ChangeCheatRequest): Cheat

    /**
     * Removes cheat.
     *
     * @param game game's UUID
     * @param uuid UUID
     */
    fun remove(game: String, uuid: String)

}
