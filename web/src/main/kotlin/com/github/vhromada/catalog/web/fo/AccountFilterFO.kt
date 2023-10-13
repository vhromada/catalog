package com.github.vhromada.catalog.web.fo

/**
 * A class represents FO for filter for accounts.
 *
 * @author Vladimir Hromada
 */
data class AccountFilterFO(
    /**
     * Username
     */
    val username: String?
) {

    /**
     * Returns query.
     *
     * @return query
     */
    fun getQuery(): String {
        val query = StringBuilder()
        query.append("?")
        if (!username.isNullOrBlank()) {
            query.append("username=$username&")
        }
        return query.toString()
    }

}
