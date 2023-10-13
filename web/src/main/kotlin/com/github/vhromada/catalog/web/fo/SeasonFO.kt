package com.github.vhromada.catalog.web.fo

import com.github.vhromada.catalog.web.validator.constraints.DateRange
import com.github.vhromada.catalog.web.validator.constraints.Years
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

/**
 * A class represents FO for season.
 *
 * @author Vladimir Hromada
 */
@Years
data class SeasonFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * Number of season
     */
    @field:Range(min = 1, max = 100)
    val number: String?,

    /**
     * Starting year
     */
    @field:DateRange
    val startYear: String?,

    /**
     * Ending year
     */
    @field:DateRange
    val endYear: String?,

    /**
     * Language
     */
    @field:NotNull
    val language: String?,

    /**
     * Subtitles
     */
    val subtitles: List<String>?,

    /**
     * Note
     */
    val note: String?
)
