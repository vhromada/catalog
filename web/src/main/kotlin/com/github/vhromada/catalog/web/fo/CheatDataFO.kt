package com.github.vhromada.catalog.web.fo

import jakarta.validation.constraints.NotBlank

/**
 * A class represents FO for cheat's data.
 *
 * @author Vladimir Hromada
 */
data class CheatDataFO(
    /**
     * Action
     */
    @field:NotBlank
    var action: String?,

    /**
     * Description
     */
    @field:NotBlank
    var description: String?
) {

    constructor() : this(action = null, description = null)

}
