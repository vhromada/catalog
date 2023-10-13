package com.github.vhromada.catalog.web.service

import com.github.vhromada.catalog.web.security.Account

/**
 * An interface represents provider for account.
 *
 * @author Vladimir Hromada
 */
interface AccountProvider {

    /**
     * Returns account.
     *
     * @return account
     */
    fun getAccount(): Account?

}
