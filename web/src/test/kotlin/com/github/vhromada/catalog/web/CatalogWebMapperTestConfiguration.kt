package com.github.vhromada.catalog.web

import com.github.vhromada.catalog.web.service.PasswordEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * A class represents Spring configuration for tests.
 *
 * @author Vladimir Hromada
 */
@Configuration
@ComponentScan("com.github.vhromada.catalog.web.mapper")
class CatalogWebMapperTestConfiguration {

    /**
     * Returns password encoder.
     *
     * @return password encoder
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {

            override fun encode(password: String): String {
                return password
            }

        }
    }

}
