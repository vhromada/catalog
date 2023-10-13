package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents song.
 *
 * @author Vladimir Hromada
 */
data class Song(
    /**
     * UUID
     */
    val uuid: String,

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
