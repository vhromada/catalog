package com.github.vhromada.catalog

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.vhromada.catalog.common.auth.AuthContextFilter
import com.github.vhromada.catalog.common.mapper.IssueMapper
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * A class represents Spring configuration for web.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(CatalogConfiguration::class)
class CatalogWebConfiguration : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
    }

    /**
     * Returns filter for auth context.
     *
     * @return swagger definition info
     */
    @Bean
    fun authContextFilter(objectMapper: ObjectMapper, issueMapper: IssueMapper): FilterRegistrationBean<AuthContextFilter> {
        val filter = FilterRegistrationBean<AuthContextFilter>()
        filter.filter = AuthContextFilter(objectMapper = objectMapper, issueMapper = issueMapper)
        filter.addUrlPatterns("/*")
        filter.order = 1
        return filter
    }

    /**
     * Returns swagger definition info.
     *
     * @return swagger definition info
     */
    @Bean
    fun catalogInfo(): OpenAPI {
        val info = Info()
            .title("Catalog")
            .description("Catalog of movies, shows, games, music, programs and books")
            .version("1.0.1")
        return OpenAPI()
            .info(info)
    }

}
