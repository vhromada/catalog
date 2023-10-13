package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.Author
import com.github.vhromada.catalog.web.connector.entity.ChangeAuthorRequest
import com.github.vhromada.catalog.web.connector.filter.AuthorFilter
import com.github.vhromada.catalog.web.fo.AuthorFO
import com.github.vhromada.catalog.web.fo.AuthorFilterFO

/**
 * An interface represents mapper for authors.
 *
 * @author Vladimir Hromada
 */
interface AuthorMapper {

    /**
     * Maps author.
     *
     * @param source author
     * @return mapped FO for author
     */
    fun map(source: Author): AuthorFO

    /**
     * Maps FO for author.
     *
     * @param source FO for author
     * @return mapped request for changing author
     */
    fun mapRequest(source: AuthorFO): ChangeAuthorRequest

    /**
     * Maps FO for filter for authors.
     *
     * @param source FO for filter for authors
     * @return mapped filter for authors
     */
    fun mapFilter(source: AuthorFilterFO): AuthorFilter

}
