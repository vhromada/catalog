package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank

/**
 * A class represents FO for author.
 *
 * @author Vladimir Hromada
 */
data class AuthorFO(
    /**
     * UUID
     */
    val uuid: String?,

    /**
     * First name
     */
    @field:NotBlank
    val firstName: String?,

    /**
     * Middle name
     */
    val middleName: String?,

    /**
     * Last name
     */
    @field:NotBlank
    val lastName: String?
)
