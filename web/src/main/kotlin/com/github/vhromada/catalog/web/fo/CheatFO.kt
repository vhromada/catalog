package com.github.vhromada.catalog.web.fo

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * A class represents FO for cheat.
 *
 * @author Vladimir Hromada
 */
data class CheatFO(
    /**
     * UUID
     */
    val uuid: String?,

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
    @field:NotNull
    @field:Size(min = 1)
    @field:Valid
    var data: List<CheatDataFO?>?
)
