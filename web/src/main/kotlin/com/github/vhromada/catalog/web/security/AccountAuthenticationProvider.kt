package com.github.vhromada.catalog.web.security

import com.github.vhromada.catalog.web.connector.AccountConnector
import com.github.vhromada.catalog.web.connector.entity.Credentials
import com.github.vhromada.catalog.web.service.PasswordEncoder
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * A class represents authentication provider for account.
 *
 * @author Vladimir Hromada
 */
class AccountAuthenticationProvider(
    /**
     * Connector for accounts
     */
    private val connector: AccountConnector,
    /**
     * Encoder for password
     */
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication!!.principal.toString()
        val password = authentication.credentials.toString()
        val encodedPassword = passwordEncoder.encode(password = password)

        val response = connector.checkCredentials(credentials = Credentials(username = username, password = encodedPassword))
        if (response.exception != null) {
            val events = response.result!!.events()
            if (events.firstOrNull { it.key == "ACCOUNT_NOT_EXIST" } != null) {
                throw UsernameNotFoundException("Account not found [username=$username]")
            }
            if (events.firstOrNull { it.key == "INVALID_CREDENTIALS" } != null) {
                throw BadCredentialsException("Invalid credentials [username=$username]")
            }
            throw InternalAuthenticationServiceException("Can't authorize [username=$username]")
        }
        val account = response.get()
        if (account.locked) {
            throw LockedException("Account is locked [username=$username]")
        }
        val result = Account(uuid = account.uuid, username = account.username, roles = account.roles)
        return UsernamePasswordAuthenticationToken(result, null, account.roles.map { SimpleGrantedAuthority(it) })
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication != null && authentication == UsernamePasswordAuthenticationToken::class.java
    }

}
