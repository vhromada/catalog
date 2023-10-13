package com.github.vhromada.catalog.web

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
class CatalogWebApplication

fun main(args: Array<String>) {
    SpringApplication.run(CatalogWebApplication::class.java, *args)
}
