package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.TestConstants
import com.github.vhromada.catalog.web.utils.TimeUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for time.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class TimeMapperTest {

    /**
     * Instance of [TimeMapper]
     */
    @Autowired
    private lateinit var mapper: TimeMapper

    /**
     * Test method for [TimeMapper.map].
     */
    @Test
    fun map() {
        val length = TestConstants.LENGTH

        val result = mapper.map(source = length)

        TimeUtils.assertTimeDeepEquals(expected = length, actual = result)
    }

    /**
     * Test method for [TimeMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val timeFO = TimeUtils.getTimeFO()

        val result = mapper.mapBack(source = timeFO)

        TimeUtils.assertTimeDeepEquals(expected = timeFO, actual = result)
    }

}
