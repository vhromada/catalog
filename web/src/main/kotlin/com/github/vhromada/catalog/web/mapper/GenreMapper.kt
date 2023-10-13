package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeGenreRequest
import com.github.vhromada.catalog.web.connector.entity.Genre
import com.github.vhromada.catalog.web.fo.GenreFO

/**
 * An interface represents mapper for genres.
 *
 * @author Vladimir Hromada
 */
interface GenreMapper {

    /**
     * Maps genre.
     *
     * @param source genre
     * @return mapped FO for genre
     */
    fun map(source: Genre): GenreFO

    /**
     * Maps FO for genre.
     *
     * @param source FO for genre
     * @return mapped request for changing genre
     */
    fun mapRequest(source: GenreFO): ChangeGenreRequest

}
