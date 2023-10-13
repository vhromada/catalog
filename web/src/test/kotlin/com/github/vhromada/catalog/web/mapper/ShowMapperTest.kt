package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.ShowUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for shows.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class ShowMapperTest {

    /**
     * Instance of [ShowMapper]
     */
    @Autowired
    private lateinit var mapper: ShowMapper

    /**
     * Test method for [ShowMapper.map].
     */
    @Test
    fun map() {
        val show = ShowUtils.getShow()

        val result = mapper.map(source = show)

        ShowUtils.assertShowDeepEquals(expected = show, actual = result)
    }

    /**
     * Test method for [ShowMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val showFO = ShowUtils.getShowFO()

        val result = mapper.mapRequest(source = showFO)

        ShowUtils.assertRequestDeepEquals(expected = showFO, actual = result)
    }

}
