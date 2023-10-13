package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank

/**
 * A class represents FO for genre.
 *
 * @author Vladimir Hromada
 */
data class GenreFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * Name
     */
    @field:NotBlank
    val name: String?
)
