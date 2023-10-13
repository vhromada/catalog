package com.github.vhromada.catalog.web.connector.filter

import com.github.vhromada.catalog.common.filter.PagingFilter
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents filter for multiple names.
 *
 * @author Vladimir Hromada
 */
data class MultipleNameFilter(
    /**
     * Czech name
     */
    val czechName: String? = null,

    /**
     * Original name
     */
    val originalName: String? = null
) : PagingFilter() {

    override fun createUrl(builder: UriComponentsBuilder) {
        super.createUrl(builder = builder)
        if (!czechName.isNullOrBlank()) {
            builder.queryParam("czechName", czechName)
        }
        if (!originalName.isNullOrBlank()) {
            builder.queryParam("originalName", originalName)
        }
    }

}
