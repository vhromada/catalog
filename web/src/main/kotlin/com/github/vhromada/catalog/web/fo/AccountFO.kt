package com.github.vhromada.catalog.web.fo

import com.github.vhromada.catalog.web.validator.constraints.Password
import com.github.vhromada.catalog.web.validator.constraints.Username
import jakarta.validation.constraints.NotBlank

/**
 * A class represents FO for account.
 *
 * @author Vladimir Hromada
 */
@Password
@Username
data class AccountFO(
    /**
     * Username
     */
    @field:NotBlank
    val username: String?,

    /**
     * Password
     */
    @field:NotBlank
    val password: String?,

    /**
     * Copy of password
     */
    @field:NotBlank
    val copyPassword: String?
)
