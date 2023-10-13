package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents movie.
 *
 * @author Vladimir Hromada
 */
data class Movie(
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
     * Year
     */
    val year: Int,

    /**
     * Languages
     */
    val languages: List<String>,

    /**
     * Subtitles
     */
    val subtitles: List<String>,

    /**
     * Media
     */
    val media: List<Medium>,

    /**
     * Length
     */
    val length: Int,

    /**
     * Formatted length
     */
    val formattedLength: String,

    /**
     * URL to ČSFD page about movie
     */
    val csfd: String?,

    /**
     * IMDB code
     */
    val imdbCode: Int?,

    /**
     * URL to english Wikipedia page about movie
     */
    val wikiEn: String?,

    /**
     * URL to czech Wikipedia page about movie
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
    val genres: List<Genre>
)
