package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.Book
import com.github.vhromada.catalog.web.connector.entity.ChangeBookRequest
import com.github.vhromada.catalog.web.fo.BookFO

/**
 * An interface represents mapper for books.
 *
 * @author Vladimir Hromada
 */
interface BookMapper {

    /**
     * Maps book.
     *
     * @param source book
     * @return mapped FO for book
     */
    fun map(source: Book): BookFO

    /**
     * Maps FO for book.
     *
     * @param source FO for book
     * @return mapped request for changing book
     */
    fun mapRequest(source: BookFO): ChangeBookRequest

}
