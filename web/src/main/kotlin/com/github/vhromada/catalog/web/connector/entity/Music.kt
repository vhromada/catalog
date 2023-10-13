package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents music.
 *
 * @author Vladimir Hromada
 */
data class Music(
    /**
     * UUID
     */
    val uuid: String,

    /**
     * Name
     */
    val name: String,

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
    val mediaCount: Int,

    /**
     * Note
     */
    val note: String?,

    /**
     * Count of songs
     */
    val songsCount: Int,

    /**
     * Length
     */
    val length: Int,

    /**
     * Formatted length
     */
    val formattedLength: String
)
