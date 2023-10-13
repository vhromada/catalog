package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.Book
import com.github.vhromada.catalog.web.connector.entity.ChangeBookRequest
import com.github.vhromada.catalog.web.fo.BookFO
import com.github.vhromada.catalog.web.mapper.BookMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for books.
 *
 * @author Vladimir Hromada
 */
@Component("bookMapper")
class BookMapperImpl : BookMapper {

    override fun map(source: Book): BookFO {
        return BookFO(
            uuid = source.uuid,
            czechName = source.czechName,
            originalName = source.originalName,
            description = source.description,
            note = source.note,
            authors = source.authors.map { it.uuid }
        )
    }

    override fun mapRequest(source: BookFO): ChangeBookRequest {
        return ChangeBookRequest(
            czechName = source.czechName!!,
            originalName = source.originalName!!,
            description = source.description!!,
            note = source.note,
            authors = source.authors!!.filterNotNull()
        )
    }

}
