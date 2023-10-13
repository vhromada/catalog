package com.github.vhromada.catalog.web.connector.filter

import com.github.vhromada.catalog.common.filter.PagingFilter
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents filter for name.
 *
 * @author Vladimir Hromada
 */
data class NameFilter(
    /**
     * Name
     */
    val name: String? = null
) : PagingFilter() {

    override fun createUrl(builder: UriComponentsBuilder) {
        super.createUrl(builder = builder)
        if (!name.isNullOrBlank()) {
            builder.queryParam("name", name)
        }
    }

}
