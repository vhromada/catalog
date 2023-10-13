package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.CheatDataUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for cheat's data.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class CheatDataMapperTest {

    /**
     * Instance of [CheatDataMapper]
     */
    @Autowired
    private lateinit var mapper: CheatDataMapper

    /**
     * Test method for [CheatDataMapper.map].
     */
    @Test
    fun map() {
        val cheatData = CheatDataUtils.getCheatData()

        val result = mapper.map(source = cheatData)

        CheatDataUtils.assertCheatDataDeepEquals(expected = cheatData, actual = result)
    }

    /**
     * Test method for [CheatDataMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val cheatDataFO = CheatDataUtils.getCheatDataFO()

        val result = mapper.mapRequest(source = cheatDataFO)

        CheatDataUtils.assertRequestDeepEquals(expected = cheatDataFO, actual = result)
    }

}
