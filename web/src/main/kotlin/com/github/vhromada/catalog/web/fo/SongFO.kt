package com.github.vhromada.catalog.web.fo

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * A class represents FO for song.
 *
 * @author Vladimir Hromada
 */
data class SongFO(
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
     * Length
     */
    @field:NotNull
    @field:Valid
    var length: TimeFO?,

    /**
     * Note
     */
    val note: String?
)
