package com.github.vhromada.catalog.web.fo

import com.github.vhromada.catalog.web.validator.constraints.DateRange
import com.github.vhromada.catalog.web.validator.constraints.Imdb
import com.github.vhromada.catalog.web.validator.constraints.ImdbCode
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * A class represents FO for movie.
 *
 * @author Vladimir Hromada
 */
@Imdb
data class MovieFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * Czech name
     */
    @field:NotBlank
    val czechName: String?,

    /**
     * Original name
     */
    @field:NotBlank
    val originalName: String?,

    /**
     * Year
     */
    @field:DateRange
    val year: String?,

    /**
     * Languages
     */
    @field:NotNull
    @field:Size(min = 1)
    val languages: List<String>?,

    /**
     * Subtitles
     */
    val subtitles: List<String>?,

    /**
     * Media
     */
    @field:NotNull
    @field:Size(min = 1)
    @field:Valid
    var media: List<TimeFO?>?,

    /**
     * URL to ČSFD page about movie
     */
    val csfd: String?,

    /**
     * True if IMDB is selected
     */
    val imdb: Boolean,

    /**
     * IMDB code
     */
    @field:ImdbCode
    val imdbCode: String?,

    /**
     * URL to english Wikipedia page about movie
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about movie
     */
    val wikiCz: String?,

    /**
     * Picture's UUID
     */
    val picture: String?,

    /**
     * Note
     */
    val note: String?,

    /**
     * Genres
     */
    @field:NotNull
    @field:Size(min = 1)
    val genres: List<String?>?
)
