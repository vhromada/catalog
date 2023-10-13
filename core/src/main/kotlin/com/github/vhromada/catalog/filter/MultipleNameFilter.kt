package com.github.vhromada.catalog.filter

import com.github.vhromada.catalog.common.filter.PagingFilter

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
) : PagingFilter()
