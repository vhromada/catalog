package com.github.vhromada.catalog.filter

import com.github.vhromada.catalog.common.filter.PagingFilter

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
) : PagingFilter()
