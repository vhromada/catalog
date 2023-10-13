package com.github.vhromada.catalog.common.entity

/**
 * A class represents page.
 *
 * @param T type of data
 * @author Vladimir Hromada
 */
data class Page<T>(
    /**
     * Data
     */
    val data: List<T>,

    /**
     * Number of page
     */
    val pageNumber: Int,

    /**
     * Count of pages
     */
    val pagesCount: Int
) {

    /**
     * Creates a new instance of [Page].
     *
     * @param data data
     * @param page page of data
     */
    constructor(data: List<T>, page: org.springframework.data.domain.Page<*>) : this(
        data = data,
        pageNumber = page.number + 1,
        pagesCount = if (page.totalPages == 0) 1 else page.totalPages
    )

}
