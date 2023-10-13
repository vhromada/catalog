package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents request for changing author.
 *
 * @author Vladimir Hromada
 */
data class ChangeAuthorRequest(
    /**
     * First name
     */
    val firstName: String,

    /**
     * Middle name
     */
    val middleName: String?,

    /**
     * Last name
     */
    val lastName: String
)
