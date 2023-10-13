package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.GameUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for games.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class GameMapperTest {

    /**
     * Instance of [GameMapper]
     */
    @Autowired
    private lateinit var mapper: GameMapper

    /**
     * Test method for [GameMapper.map].
     */
    @Test
    fun map() {
        val game = GameUtils.getGame()

        val result = mapper.map(source = game)

        GameUtils.assertGameDeepEquals(expected = game, actual = result)
    }

    /**
     * Test method for [GameMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val gameFO = GameUtils.getGameFO()

        val result = mapper.mapRequest(source = gameFO)

        GameUtils.assertRequestDeepEquals(expected = gameFO, actual = result)
    }

}
