package com.github.vhromada.catalog.web.validator

import com.github.vhromada.catalog.common.filter.FieldOperation
import com.github.vhromada.catalog.web.connector.AccountConnector
import com.github.vhromada.catalog.web.connector.filter.AccountFilter
import com.github.vhromada.catalog.web.fo.AccountFO
import com.github.vhromada.catalog.web.service.AccountProvider
import com.github.vhromada.catalog.web.validator.constraints.Username
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired

/**
 * A class represents validator for username constraint.
 *
 * @author Vladimir Hromada
 */
class UsernameValidator : ConstraintValidator<Username, AccountFO> {

    /**
     * Connector for accounts
     */
    @Autowired
    private lateinit var connector: AccountConnector

    /**
     * Provider for account
     */
    @Autowired
    private lateinit var accountProvider: AccountProvider

    override fun isValid(account: AccountFO?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (account == null) {
            return false
        }
        if (accountProvider.getAccount()?.username == account.username) {
            return true
        }
        val filter = AccountFilter(
            username = account.username!!,
            usernameOperation = FieldOperation.EQ
        )
        filter.page = 1
        filter.limit = 1
        return connector.search(filter = filter).data.isEmpty()
    }

}
