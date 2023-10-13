package com.github.vhromada.catalog.web.fo

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

/**
 * A class represents FO for episode.
 *
 * @author Vladimir Hromada
 */
data class EpisodeFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * Number of episode
     */
    @field:Range(min = 1, max = 500)
    val number: String?,

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
