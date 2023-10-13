package com.github.vhromada.catalog.web.fo

/**
 * A class represents FO for filter for authors.
 *
 * @author Vladimir Hromada
 */
data class AuthorFilterFO(
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
) {

    /**
     * Returns query.
     *
     * @return query
     */
    fun getQuery(): String {
        val query = StringBuilder()
        query.append("?")
        if (!firstName.isNullOrBlank()) {
            query.append("firstName=$firstName&")
        }
        if (!middleName.isNullOrBlank()) {
            query.append("middleName=$middleName&")
        }
        if (!lastName.isNullOrBlank()) {
            query.append("lastName=$lastName&")
        }
        return query.toString()
    }

}
