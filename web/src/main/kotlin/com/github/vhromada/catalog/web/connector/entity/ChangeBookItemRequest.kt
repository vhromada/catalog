package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents request for changing book item.
 *
 * @author Vladimir Hromada
 */
data class ChangeBookItemRequest(
    /**
     * Languages
     */
    val languages: List<String>,

    /**
     * Format
     */
    val format: String,

    /**
     * Note
     */
    val note: String?
)
