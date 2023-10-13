package com.github.vhromada.catalog.web.connector.common

import org.apache.hc.client5.http.auth.AuthScope
import org.apache.hc.client5.http.auth.BasicUserPrincipal
import org.apache.hc.client5.http.auth.Credentials
import org.apache.hc.client5.http.auth.CredentialsProvider
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider
import java.security.Principal
import java.util.function.Supplier

/**
 * A class represents config for connector.
 *
 * @author Vladimir Hromada
 */
data class ConnectorConfig(
    /**
     * URL
     */
    val url: Supplier<String>,

    /**
     * Username
     */
    private val username: Supplier<String>,

    /**
     * Password
     */
    private val password: Supplier<String>
) {

    /**
     * Returns provider for credentials.
     *
     * @return provider for credentials
     */
    fun getCredentialsProvider(): CredentialsProvider {
        val provider = BasicCredentialsProvider()
        provider.setCredentials(AuthScope(null, -1), getCredentials(username = username.get(), password = password.get()))
        return provider
    }

    /**
     * Returns credentials.
     *
     * @param username username
     * @param password password
     * @return credentials
     */
    private fun getCredentials(username: String, password: String): Credentials {
        return object : Credentials {
            override fun getUserPrincipal(): Principal {
                return BasicUserPrincipal(username)
            }

            override fun getPassword(): CharArray {
                return password.toCharArray()
            }
        }
    }

}
