package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents request for changing cheat.
 *
 * @author Vladimir Hromada
 */
data class ChangeCheatRequest(
    /**
     * Setting for game
     */
    val gameSetting: String?,

    /**
     * Setting for cheat
     */
    val cheatSetting: String?,

    /**
     * Data
     */
    val data: List<ChangeCheatData>
)
