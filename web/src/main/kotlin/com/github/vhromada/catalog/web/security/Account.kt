package com.github.vhromada.catalog.web.security

/**
 * A class represents account.
 *
 * @author Vladimir Hromada
 */
data class Account(
    /**
     * UUID
     */
    val uuid: String,

    /**
     * Username
     */
    val username: String,

    /**
     * Roles
     */
    val roles: List<String>
)
