package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents show.
 *
 * @author Vladimir Hromada
 */
data class Show(
    /**
     * UUID
     */
    val uuid: String,

    /**
     * Czech name
     */
    val czechName: String,

    /**
     * Original name
     */
    val originalName: String,

    /**
     * URL to ČSFD page about show
     */
    val csfd: String?,

    /**
     * IMDB code
     */
    val imdbCode: Int?,

    /**
     * URL to english Wikipedia page about show
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about show
     */
    val wikiCz: String?,

    /**
     * Picture
     */
    val picture: String?,

    /**
     * Note
     */
    val note: String?,

    /**
     * Genres
     */
    val genres: List<Genre>,

    /**
     * Count of seasons
     */
    val seasonsCount: Int,

    /**
     * Count of episodes
     */
    val episodesCount: Int,

    /**
     * Length
     */
    val length: Int,

    /**
     * Formatted length
     */
    val formattedLength: String
)
