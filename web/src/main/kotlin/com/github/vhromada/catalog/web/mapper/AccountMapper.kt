package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.Credentials
import com.github.vhromada.catalog.web.connector.filter.AccountFilter
import com.github.vhromada.catalog.web.fo.AccountFO
import com.github.vhromada.catalog.web.fo.AccountFilterFO

/**
 * An interface represents mapper for accounts.
 *
 * @author Vladimir Hromada
 */
interface AccountMapper {

    /**
     * Maps FO for account.
     *
     * @param source FO for account
     * @return mapped credentials
     */
    fun map(source: AccountFO): Credentials

    /**
     * Maps FO for filter for accounts.
     *
     * @param source FO for filter for accounts
     * @return mapped filter for accounts
     */
    fun mapFilter(source: AccountFilterFO): AccountFilter

}
