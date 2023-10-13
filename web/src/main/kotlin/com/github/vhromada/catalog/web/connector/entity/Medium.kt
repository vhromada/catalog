package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents medium.
 *
 * @author Vladimir Hromada
 */
data class Medium(
    /**
     * Number
     */
    val number: Int,

    /**
     * Length
     */
    val length: Int,

    /**
     * Formatted length
     */
    val formattedLength: String
)
