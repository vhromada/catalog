package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.MusicUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for music.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class MusicMapperTest {

    /**
     * Instance of [MusicMapper]
     */
    @Autowired
    private lateinit var mapper: MusicMapper

    /**
     * Test method for [MusicMapper.map].
     */
    @Test
    fun map() {
        val music = MusicUtils.getMusic()

        val result = mapper.map(source = music)

        MusicUtils.assertMusicDeepEquals(expected = music, actual = result)
    }

    /**
     * Test method for [MusicMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val musicFO = MusicUtils.getMusicFO()

        val result = mapper.mapRequest(source = musicFO)

        MusicUtils.assertRequestDeepEquals(expected = musicFO, actual = result)
    }

}
