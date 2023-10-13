package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.BookItem
import com.github.vhromada.catalog.web.connector.entity.ChangeBookItemRequest
import com.github.vhromada.catalog.web.fo.BookItemFO
import com.github.vhromada.catalog.web.mapper.BookItemMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for book items.
 *
 * @author Vladimir Hromada
 */
@Component("bookItemMapper")
class BookItemMapperImpl : BookItemMapper {

    override fun map(source: BookItem): BookItemFO {
        return BookItemFO(
            uuid = source.uuid,
            languages = source.languages,
            format = source.format,
            note = source.note
        )
    }

    override fun mapRequest(source: BookItemFO): ChangeBookItemRequest {
        return ChangeBookItemRequest(
            languages = source.languages!!,
            format = source.format!!,
            note = source.note
        )
    }

}
