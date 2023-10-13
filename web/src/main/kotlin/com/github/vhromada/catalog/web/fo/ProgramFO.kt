package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

/**
 * A class represents FO for program.
 *
 * @author Vladimir Hromada
 */
data class ProgramFO(
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
     * URL to english Wikipedia page about program
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about program
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
     * Other data
     */
    val otherData: String?,

    /**
     * Note
     */
    val note: String?
)
