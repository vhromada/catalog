package com.github.vhromada.catalog.validator

import com.github.vhromada.catalog.common.exception.InputException
import com.github.vhromada.catalog.entity.ChangeBookItemRequest

/**
 * An interface represents validator for book items.
 *
 * @author Vladimir Hromada
 */
interface BookItemValidator {

    /**
     * Validates request for changing book item.
     * <br></br>
     * Validation errors:
     *
     *  * Languages are null
     *  * Languages contain null value
     *  * Format is null
     *
     * @param request request for changing book item
     * @throws InputException if request for changing book item isn't valid
     */
    fun validateRequest(request: ChangeBookItemRequest)

}
