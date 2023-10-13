package com.github.vhromada.catalog.web.connector.entity

/**
 * A class represents request for changing roles.
 *
 * @author Vladimir Hromada
 */
data class ChangeRolesRequest(
    /**
     * Roles
     */
    val roles: List<String>
)
