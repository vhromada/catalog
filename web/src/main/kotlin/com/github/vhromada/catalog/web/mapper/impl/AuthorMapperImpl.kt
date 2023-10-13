package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.Author
import com.github.vhromada.catalog.web.connector.entity.ChangeAuthorRequest
import com.github.vhromada.catalog.web.connector.filter.AuthorFilter
import com.github.vhromada.catalog.web.fo.AuthorFO
import com.github.vhromada.catalog.web.fo.AuthorFilterFO
import com.github.vhromada.catalog.web.mapper.AuthorMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for authors.
 *
 * @author Vladimir Hromada
 */
@Component("authorMapper")
class AuthorMapperImpl : AuthorMapper {

    override fun map(source: Author): AuthorFO {
        return AuthorFO(
            uuid = source.uuid,
            firstName = source.firstName,
            middleName = source.middleName,
            lastName = source.lastName
        )
    }

    override fun mapRequest(source: AuthorFO): ChangeAuthorRequest {
        return ChangeAuthorRequest(
            firstName = source.firstName!!,
            middleName = source.middleName,
            lastName = source.lastName!!
        )
    }

    override fun mapFilter(source: AuthorFilterFO): AuthorFilter {
        return AuthorFilter(
            firstName = source.firstName,
            middleName = source.middleName,
            lastName = source.lastName
        )
    }

}
