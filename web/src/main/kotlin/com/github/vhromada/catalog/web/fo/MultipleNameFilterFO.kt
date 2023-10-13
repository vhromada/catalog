package com.github.vhromada.catalog.web.fo

/**
 * A class represents FO for filter for multiple names.
 *
 * @author Vladimir Hromada
 */
data class MultipleNameFilterFO(
    /**
     * Czech name
     */
    val czechName: String?,

    /**
     * Original name
     */
    val originalName: String?
) {

    /**
     * Returns query.
     *
     * @return query
     */
    fun getQuery(): String {
        val query = StringBuilder()
        query.append("?")
        if (!czechName.isNullOrBlank()) {
            query.append("czechName=$czechName&")
        }
        if (!originalName.isNullOrBlank()) {
            query.append("originalName=$originalName&")
        }
        return query.toString()
    }

}
