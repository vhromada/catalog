package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.SeasonUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for seasons.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class SeasonMapperTest {

    /**
     * Instance of [SeasonMapper]
     */
    @Autowired
    private lateinit var mapper: SeasonMapper

    /**
     * Test method for [SeasonMapper.map].
     */
    @Test
    fun map() {
        val season = SeasonUtils.getSeason()

        val result = mapper.map(source = season)

        SeasonUtils.assertSeasonDeepEquals(expected = season, actual = result)
    }

    /**
     * Test method for [SeasonMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val seasonFO = SeasonUtils.getSeasonFO()

        val result = mapper.mapRequest(source = seasonFO)

        SeasonUtils.assertRequestDeepEquals(expected = seasonFO, actual = result)
    }

}
