package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeGenreRequest
import com.github.vhromada.catalog.web.connector.entity.Genre
import com.github.vhromada.catalog.web.fo.GenreFO
import com.github.vhromada.catalog.web.mapper.GenreMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for genres.
 *
 * @author Vladimir Hromada
 */
@Component("genreMapper")
class GenreMapperImpl : GenreMapper {

    override fun map(source: Genre): GenreFO {
        return GenreFO(
            uuid = source.uuid,
            name = source.name
        )
    }

    override fun mapRequest(source: GenreFO): ChangeGenreRequest {
        return ChangeGenreRequest(name = source.name!!)
    }

}
