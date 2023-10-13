package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeMovieRequest
import com.github.vhromada.catalog.web.connector.entity.Movie
import com.github.vhromada.catalog.web.fo.MovieFO
import com.github.vhromada.catalog.web.mapper.MovieMapper
import com.github.vhromada.catalog.web.mapper.TimeMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for movies.
 *
 * @author Vladimir Hromada
 */
@Component("movieMapper")
class MovieMapperImpl(
    /**
     * Mapper for time
     */
    private val mapper: TimeMapper
) : MovieMapper {

    override fun map(source: Movie): MovieFO {
        return MovieFO(
            uuid = source.uuid,
            czechName = source.czechName,
            originalName = source.originalName,
            year = source.year.toString(),
            languages = source.languages,
            subtitles = source.subtitles,
            media = source.media.map { mapper.map(source = it.length) },
            csfd = source.csfd,
            imdb = source.imdbCode != null && source.imdbCode >= 1,
            imdbCode = if (source.imdbCode == null || source.imdbCode < 1) null else source.imdbCode.toString(),
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            picture = source.picture,
            note = source.note,
            genres = source.genres.map { it.uuid }
        )
    }

    override fun mapRequest(source: MovieFO): ChangeMovieRequest {
        return ChangeMovieRequest(
            czechName = source.czechName!!,
            originalName = source.originalName!!,
            year = source.year!!.toInt(),
            languages = source.languages!!,
            subtitles = source.subtitles!!,
            media = source.media!!.map { mapper.mapBack(source = it!!) },
            csfd = source.csfd,
            imdbCode = if (source.imdb) source.imdbCode!!.toInt() else -1,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            picture = source.picture,
            note = source.note,
            genres = source.genres!!.filterNotNull()
        )
    }

}
