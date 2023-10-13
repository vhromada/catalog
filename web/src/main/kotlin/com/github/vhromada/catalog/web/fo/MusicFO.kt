package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Range

/**
 * A class represents FO for music.
 *
 * @author Vladimir Hromada
 */
data class MusicFO(
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
     * URL to english Wikipedia page about music
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about music
     */
    val wikiCz: String?,

    /**
     * Count of media
     */
    @field:Range(min = 1, max = 100)
    val mediaCount: String?,

    /**
     * Note
     */
    val note: String?
)
