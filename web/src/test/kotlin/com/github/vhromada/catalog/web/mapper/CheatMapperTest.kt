package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.CheatUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for cheats.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class CheatMapperTest {

    /**
     * Instance of [CheatMapper]
     */
    @Autowired
    private lateinit var mapper: CheatMapper

    /**
     * Test method for [CheatMapper.map].
     */
    @Test
    fun map() {
        val cheat = CheatUtils.getCheat()

        val result = mapper.map(source = cheat)

        CheatUtils.assertCheatDeepEquals(expected = cheat, actual = result)
    }

    /**
     * Test method for [CheatMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val cheatFO = CheatUtils.getCheatFO()

        val result = mapper.mapRequest(source = cheatFO)

        CheatUtils.assertRequestDeepEquals(expected = cheatFO, actual = result)
    }

}
