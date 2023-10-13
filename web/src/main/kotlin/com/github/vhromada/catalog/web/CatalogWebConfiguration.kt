package com.github.vhromada.catalog.web

import com.github.vhromada.catalog.web.connector.AccountConnector
import com.github.vhromada.catalog.web.security.AccountAuthenticationProvider
import com.github.vhromada.catalog.web.service.PasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * A class represents Spring configuration for catalog.
 *
 * @author Vladimir Hromada
 */
@Configuration
class CatalogWebConfiguration : WebMvcConfigurer {

    /**
     * Returns provider for authentication.
     *
     * @param connector       connector for accounts
     * @param passwordEncoder encoder for password
     * @return provider for authentication
     */
    @Bean
    fun authenticationProvider(
        connector: AccountConnector,
        passwordEncoder: PasswordEncoder
    ): AuthenticationProvider {
        return AccountAuthenticationProvider(connector = connector, passwordEncoder = passwordEncoder)
    }

}
