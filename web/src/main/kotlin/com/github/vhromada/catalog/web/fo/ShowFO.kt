package com.github.vhromada.catalog.web.fo

import com.github.vhromada.catalog.web.validator.constraints.Imdb
import com.github.vhromada.catalog.web.validator.constraints.ImdbCode
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * A class represents FO for show.
 *
 * @author Vladimir Hromada
 */
@Imdb
data class ShowFO(
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
     * URL to ČSFD page about show
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
     * URL to english Wikipedia page about show
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about show
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
