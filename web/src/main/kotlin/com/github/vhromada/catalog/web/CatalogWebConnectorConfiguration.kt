package com.github.vhromada.catalog.web

import com.github.vhromada.catalog.web.connector.common.ConnectorConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * A class represents Spring configuration for connector.
 *
 * @author Vladimir Hromada
 */
@Configuration
class CatalogWebConnectorConfiguration(
    /**
     * URL
     */
    @Value("\${catalog.connector.url:}") private val url: String,
    /**
     * Username
     */
    @Value("\${catalog.connector.username:}") private val username: String,
    /**
     * Password
     */
    @Value("\${catalog.connector.password:}") private val password: String
) {

    /**
     * Returns connector configuration for catalog.
     *
     * @return connector configuration for catalog
     */
    @Bean
    fun catalogConfig(): ConnectorConfig {
        return ConnectorConfig(url = { url }, username = { username }, password = { password })
    }

}
