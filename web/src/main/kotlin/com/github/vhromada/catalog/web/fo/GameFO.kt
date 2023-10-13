package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

/**
 * A class represents FO for game.
 *
 * @author Vladimir Hromada
 */
data class GameFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * Name
     */
    @field:NotBlank
    val name: String?,

    /**
     * URL to english Wikipedia page about game
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about game
     */
    val wikiCz: String?,

    /**
     * Count of media
     */
    @field:Range(min = 1, max = 100)
    val mediaCount: String?,

    /**
     * Format
     */
    @field:NotNull
    val format: String?,

    /**
     * True if there is crack
     */
    val crack: Boolean?,

    /**
     * True if there is serial key
     */
    val serialKey: Boolean?,

    /**
     * True if there is patch
     */
    val patch: Boolean?,

    /**
     * True if there is trainer
     */
    val trainer: Boolean?,

    /**
     * True if there is data for trainer
     */
    val trainerData: Boolean?,

    /**
     * True if there is editor
     */
    val editor: Boolean?,

    /**
     * True if there are saves
     */
    val saves: Boolean?,

    /**
     * Other data
     */
    val otherData: String?,

    /**
     * Note
     */
    val note: String?
)
