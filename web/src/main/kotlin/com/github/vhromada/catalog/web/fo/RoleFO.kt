package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * A class represents FO for role.
 *
 * @author Vladimir Hromada
 */
data class RoleFO(
    /**
     * Roles
     */
    @field:NotNull
    @field:Size(min = 1)
    val roles: List<String>?
)
