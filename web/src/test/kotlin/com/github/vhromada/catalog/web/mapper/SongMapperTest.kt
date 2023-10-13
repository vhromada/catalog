package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.SongUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for songs.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class SongMapperTest {

    /**
     * Instance of [SongMapper]
     */
    @Autowired
    private lateinit var mapper: SongMapper

    /**
     * Test method for [SongMapper.map].
     */
    @Test
    fun map() {
        val song = SongUtils.getSong()

        val result = mapper.map(source = song)

        SongUtils.assertSongDeepEquals(expected = song, actual = result)
    }

    /**
     * Test method for [SongMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val songFO = SongUtils.getSongFO()

        val result = mapper.mapRequest(source = songFO)

        SongUtils.assertRequestDeepEquals(expected = songFO, actual = result)
    }

}
