package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeMovieRequest
import com.github.vhromada.catalog.web.connector.entity.Movie
import com.github.vhromada.catalog.web.fo.MovieFO

/**
 * An interface represents mapper for movies.
 *
 * @author Vladimir Hromada
 */
interface MovieMapper {

    /**
     * Maps movie.
     *
     * @param source movie
     * @return mapped FO for movie
     */
    fun map(source: Movie): MovieFO

    /**
     * Maps FO for movie.
     *
     * @param source FO for movie
     * @return mapped request for changing movie
     */
    fun mapRequest(source: MovieFO): ChangeMovieRequest

}
