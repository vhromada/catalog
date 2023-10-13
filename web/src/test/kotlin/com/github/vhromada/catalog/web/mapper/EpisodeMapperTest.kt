package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.EpisodeUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for episodes.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class EpisodeMapperTest {

    /**
     * Instance of [EpisodeMapper]
     */
    @Autowired
    private lateinit var mapper: EpisodeMapper

    /**
     * Test method for [EpisodeMapper.map].
     */
    @Test
    fun map() {
        val episode = EpisodeUtils.getEpisode()

        val result = mapper.map(source = episode)

        EpisodeUtils.assertEpisodeDeepEquals(expected = episode, actual = result)
    }

    /**
     * Test method for [EpisodeMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val episodeFO = EpisodeUtils.getEpisodeFO()

        val result = mapper.mapRequest(source = episodeFO)

        EpisodeUtils.assertRequestDeepEquals(expected = episodeFO, actual = result)
    }

}
