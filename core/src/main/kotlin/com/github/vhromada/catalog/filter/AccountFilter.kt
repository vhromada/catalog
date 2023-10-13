package com.github.vhromada.catalog.filter

import com.github.vhromada.catalog.common.filter.FieldOperation
import com.github.vhromada.catalog.common.filter.PagingFilter

/**
 * A class represents filter for accounts.
 *
 * @author Vladimir Hromada
 */
data class AccountFilter(
    /**
     * UUID
     */
    val uuid: String? = null,

    /**
     * Username
     */
    val username: String? = null,

    /**
     * Operation for username
     */
    val usernameOperation: FieldOperation? = null
) : PagingFilter()
