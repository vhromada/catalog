package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeCheatRequest
import com.github.vhromada.catalog.web.connector.entity.Cheat
import com.github.vhromada.catalog.web.fo.CheatFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for cheats.
 *
 * @author Vladimir Hromada
 */
object CheatUtils {

    /**
     * Returns FO for cheat.
     *
     * @return FO for cheat
     */
    fun getCheatFO(): CheatFO {
        return CheatFO(
            uuid = TestConstants.UUID,
            gameSetting = "Game setting",
            cheatSetting = "Cheat setting",
            data = listOf(CheatDataUtils.getCheatDataFO())
        )
    }

    /**
     * Returns cheat.
     *
     * @return cheat
     */
    fun getCheat(): Cheat {
        return Cheat(
            uuid = TestConstants.UUID,
            gameSetting = "Game setting",
            cheatSetting = "Cheat setting",
            data = listOf(CheatDataUtils.getCheatData())
        )
    }

    /**
     * Asserts cheat deep equals.
     *
     * @param expected expected cheat
     * @param actual   actual FO for cheat
     */
    fun assertCheatDeepEquals(expected: Cheat, actual: CheatFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.gameSetting).isEqualTo(expected.gameSetting)
            it.assertThat(actual.cheatSetting).isEqualTo(expected.cheatSetting)
        }
        CheatDataUtils.assertCheatDataListDeepEquals(expected = expected.data, actual = actual.data)
    }

    /**
     * Asserts FO for cheat and request deep equals.
     *
     * @param expected expected FO for cheat
     * @param actual   actual request for changing cheat
     */
    fun assertRequestDeepEquals(expected: CheatFO, actual: ChangeCheatRequest) {
        assertSoftly {
            it.assertThat(actual.gameSetting).isEqualTo(expected.gameSetting)
            it.assertThat(actual.cheatSetting).isEqualTo(expected.cheatSetting)
        }
        CheatDataUtils.assertRequestsDeepEquals(expected = expected.data, actual = actual.data)
    }

}
