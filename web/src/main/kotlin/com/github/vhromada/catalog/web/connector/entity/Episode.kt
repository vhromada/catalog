package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents episode.
 *
 * @author Vladimir Hromada
 */
data class Episode(
    /**
     * UUID
     */
    val uuid: String,

    /**
     * Number of episode
     */
    val number: Int,

    /**
     * Name
     */
    val name: String,

    /**
     * Length
     */
    val length: Int,

    /**
     * Formatted length
     */
    val formattedLength: String,

    /**
     * Note
     */
    val note: String?
)
