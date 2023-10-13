package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.BookItem
import com.github.vhromada.catalog.web.connector.entity.ChangeBookItemRequest
import com.github.vhromada.catalog.web.fo.BookItemFO

/**
 * An interface represents mapper for book items.
 *
 * @author Vladimir Hromada
 */
interface BookItemMapper {

    /**
     * Maps book item.
     *
     * @param source book item
     * @return mapped FO for book item
     */
    fun map(source: BookItem): BookItemFO

    /**
     * Maps FO for book item.
     *
     * @param source FO for book item
     * @return mapped request for changing book item
     */
    fun mapRequest(source: BookItemFO): ChangeBookItemRequest

}
