package com.github.vhromada.catalog.web.connector.filter

import com.github.vhromada.catalog.common.filter.FieldOperation
import com.github.vhromada.catalog.common.filter.PagingFilter
import org.springframework.web.util.UriComponentsBuilder

/**
 * A class represents filter for accounts.
 *
 * @author Vladimir Hromada
 */
data class AccountFilter(
    /**
     * Username
     */
    val username: String? = null,

    /**
     * Operation for username
     */
    val usernameOperation: FieldOperation? = null
) : PagingFilter() {

    override fun createUrl(builder: UriComponentsBuilder) {
        super.createUrl(builder = builder)
        if (!username.isNullOrBlank()) {
            builder.queryParam("username", username)
        }
        if (usernameOperation != null) {
            builder.queryParam("usernameOperation", usernameOperation)
        }
    }

}
