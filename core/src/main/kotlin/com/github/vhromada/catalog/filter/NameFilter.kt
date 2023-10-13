package com.github.vhromada.catalog.filter

import com.github.vhromada.catalog.common.filter.PagingFilter

/**
 * A class represents filter for names.
 *
 * @author Vladimir Hromada
 */
data class NameFilter(
    /**
     * Name
     */
    val name: String? = null
) : PagingFilter()
