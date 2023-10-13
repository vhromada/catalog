package com.github.vhromada.catalog.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

/**
 * A class represents Spring configuration for security.
 *
 * @author Vladimir Hromada
 */
@Configuration
@EnableWebSecurity
class CatalogWebSecurityConfiguration {

    /**
     * Returns filter chain.
     *
     * @param http HTTP security
     * @return filter chain
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.requestMatchers("/login", "/login/error", "/registration").permitAll()
                .requestMatchers("/css/**", "/js/**").permitAll()
                .requestMatchers("/logout").authenticated()
                .requestMatchers("/accounts", "/accounts", "/accounts/**/roles/**").hasAnyRole("ADMIN")
                .anyRequest().hasAnyRole("ADMIN", "USER")
        }
        http.formLogin {
            it.loginPage("/login")
                .failureUrl("/login/error")
        }
        http.exceptionHandling { it.accessDeniedPage("/access-denied") }
        return http.build()
    }

}
