package com.github.vhromada.catalog.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.web.WebAppConfiguration

/**
 * A class represents context test.
 *
 * @author Vladimir Hromada
 */
@SpringBootTest
@WebAppConfiguration
class CatalogWebContextTest {

    /**
     * Instance of [ApplicationContext]
     */
    @Autowired
    private lateinit var context: ApplicationContext

    /**
     * Test method for Spring context.
     */
    @Test
    fun app() {
        KotlinLogging.logger {}.info { "Spring context test - OK [beans=${context.beanDefinitionNames.size}]" }
    }

}
