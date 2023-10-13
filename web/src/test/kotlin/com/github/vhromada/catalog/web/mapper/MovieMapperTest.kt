package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.MovieUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for movies.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class MovieMapperTest {

    /**
     * Instance of [MovieMapper]
     */
    @Autowired
    private lateinit var mapper: MovieMapper

    /**
     * Test method for [MovieMapper.map].
     */
    @Test
    fun map() {
        val movie = MovieUtils.getMovie()

        val result = mapper.map(source = movie)

        MovieUtils.assertMovieDeepEquals(expected = movie, actual = result)
    }

    /**
     * Test method for [MovieMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val movieFO = MovieUtils.getMovieFO()

        val result = mapper.mapRequest(source = movieFO)

        MovieUtils.assertRequestDeepEquals(expected = movieFO, actual = result)
    }

}
