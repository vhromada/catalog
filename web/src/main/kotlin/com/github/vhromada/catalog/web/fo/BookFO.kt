package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * A class represents FO for book.
 *
 * @author Vladimir Hromada
 */
data class BookFO(
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
     * Description
     */
    @field:NotBlank
    val description: String?,

    /**
     * Note
     */
    val note: String?,

    /**
     * Authors
     */
    @field:NotNull
    @field:Size(min = 1)
    val authors: List<String?>?
)
