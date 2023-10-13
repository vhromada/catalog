package com.github.vhromada.catalog.web.connector

/**
 * An interface represents connector for roles.
 *
 * @author Vladimir Hromada
 */
interface RoleConnector {

    /**
     * Returns list of roles.
     *
     * @return list of roles
     */
    fun getRoles(): List<String>

}
