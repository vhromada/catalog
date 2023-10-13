package com.github.vhromada.catalog.web.service.impl

import com.github.vhromada.catalog.web.security.Account
import com.github.vhromada.catalog.web.service.AccountProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * A class represents implementation of provider for account.
 *
 * @author Vladimir Hromada
 */
@Component
class AccountProviderImpl : AccountProvider {

    override fun getAccount(): Account? {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication is UsernamePasswordAuthenticationToken) {
            authentication.principal as Account
        } else {
            null
        }
    }

}
