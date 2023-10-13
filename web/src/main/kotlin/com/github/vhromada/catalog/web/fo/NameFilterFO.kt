package com.github.vhromada.catalog.web.fo

/**
 * A class represents FO for filter for name.
 *
 * @author Vladimir Hromada
 */
data class NameFilterFO(
    /**
     * Name
     */
    val name: String?
) {

    /**
     * Returns query.
     *
     * @return query
     */
    fun getQuery(): String {
        val query = StringBuilder()
        query.append("?")
        if (!name.isNullOrBlank()) {
            query.append("name=$name&")
        }
        return query.toString()
    }

}
