package com.github.vhromada.catalog.web.connector.filter

import com.github.vhromada.catalog.common.filter.PagingFilter
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents filter for authors.
 *
 * @author Vladimir Hromada
 */
data class AuthorFilter(
    /**
     * First name
     */
    val firstName: String? = null,

    /**
     * Middle name
     */
    val middleName: String? = null,

    /**
     * Last name
     */
    val lastName: String? = null
) : PagingFilter() {

    override fun createUrl(builder: UriComponentsBuilder) {
        super.createUrl(builder = builder)
        if (!firstName.isNullOrBlank()) {
            builder.queryParam("firstName", firstName)
        }
        if (!middleName.isNullOrBlank()) {
            builder.queryParam("middleName", middleName)
        }
        if (!lastName.isNullOrBlank()) {
            builder.queryParam("lastName", lastName)
        }
    }

}
