package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotNull

/**
 * A class represents FO for book item.
 *
 * @author Vladimir Hromada
 */
data class BookItemFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * Languages
     */
    val languages: List<String>?,

    /**
     * Format
     */
    @field:NotNull
    val format: String?,

    /**
     * Note
     */
    val note: String?
)
