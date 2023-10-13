package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.Credentials
import com.github.vhromada.catalog.web.connector.filter.AccountFilter
import com.github.vhromada.catalog.web.fo.AccountFO
import com.github.vhromada.catalog.web.fo.AccountFilterFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for accounts.
 *
 * @author Vladimir Hromada
 */
object AccountUtils {

    /**
     * Returns FO for account.
     *
     * @return FO for account
     */
    fun getAccount(): AccountFO {
        return AccountFO(
            username = "username",
            password = "password",
            copyPassword = "copyPassword"
        )
    }

    /**
     * Returns FO for filter for accounts.
     *
     * @return FO for filter for accounts
     */
    fun getFilter(): AccountFilterFO {
        return AccountFilterFO(
            username = "username"
        )
    }

    /**
     * Asserts account deep equals.
     *
     * @param expected expected FO for account
     * @param actual   actual credentials
     */
    fun assertAccountDeepEquals(expected: AccountFO, actual: Credentials) {
        assertSoftly {
            it.assertThat(actual.username).isEqualTo(expected.username)
            it.assertThat(actual.password).isEqualTo(expected.password)
        }
    }

    /**
     * Asserts filter for accounts deep equals.
     *
     * @param expected expected FO for filter for accounts
     * @param actual   actual filter for accounts
     */
    fun assertAccountFilterDeepEquals(expected: AccountFilterFO, actual: AccountFilter) {
        assertSoftly {
            it.assertThat(actual.username).isEqualTo(expected.username)
            it.assertThat(actual.page).isNull()
            it.assertThat(actual.limit).isNull()
        }
    }

}
