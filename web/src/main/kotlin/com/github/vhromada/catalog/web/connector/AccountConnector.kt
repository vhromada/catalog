package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.common.RestResponse
import com.github.vhromada.catalog.web.connector.entity.Account
import com.github.vhromada.catalog.web.connector.entity.AccountStatistics
import com.github.vhromada.catalog.web.connector.entity.ChangeRolesRequest
import com.github.vhromada.catalog.web.connector.entity.Credentials
import com.github.vhromada.catalog.web.connector.filter.AccountFilter

/**
 * An interface represents connector for accounts.
 *
 * @author Vladimir Hromada
 */
interface AccountConnector {

    /**
     * Returns list of accounts for filter.
     *
     * @param filter filter
     * @return list of accounts for filter
     */
    fun search(filter: AccountFilter): Page<Account>

    /**
     * Returns account.
     *
     * @param uuid UUID
     * @return account
     */
    fun get(uuid: String): Account

    /**
     * Updates credentials.
     *
     * @param uuid        UUID
     * @param credentials credentials
     * @return updated account
     */
    fun updateCredentials(uuid: String, credentials: Credentials): Account

    /**
     * Updates credentials.
     *
     * @param uuid    UUID
     * @param request request for changing roles
     * @return updated account
     */
    fun updateRoles(uuid: String, request: ChangeRolesRequest): Account

    /**
     * Returns statistics.
     *
     * @return statistics
     */
    fun getStatistics(): AccountStatistics

    /**
     * Adds credentials.
     *
     * @param credentials credentials
     * @return created account
     */
    fun addCredentials(credentials: Credentials): Account

    /**
     * Checks credentials.
     *
     * @param credentials credentials
     * @return response with account or error
     */
    fun checkCredentials(credentials: Credentials): RestResponse<Account>

}
