package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.GenreUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for genres.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class GenreMapperTest {

    /**
     * Instance of [GenreMapper]
     */
    @Autowired
    private lateinit var mapper: GenreMapper

    /**
     * Test method for [GenreMapper.map].
     */
    @Test
    fun map() {
        val genre = GenreUtils.getGenre()

        val result = mapper.map(source = genre)

        GenreUtils.assertGenreDeepEquals(expected = genre, actual = result)
    }

    /**
     * Test method for [GenreMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val genreFO = GenreUtils.getGenreFO()

        val result = mapper.mapRequest(source = genreFO)

        GenreUtils.assertRequestDeepEquals(expected = genreFO, actual = result)
    }

}
