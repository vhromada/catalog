package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeGameRequest
import com.github.vhromada.catalog.web.connector.entity.Game
import com.github.vhromada.catalog.web.fo.GameFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for games.
 *
 * @author Vladimir Hromada
 */
object GameUtils {

    /**
     * Returns FO for game.
     *
     * @return FO for game
     */
    fun getGameFO(): GameFO {
        return GameFO(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            mediaCount = TestConstants.MEDIA.toString(),
            format = TestConstants.FORMAT,
            crack = true,
            serialKey = true,
            patch = true,
            trainer = true,
            trainerData = true,
            editor = true,
            saves = true,
            otherData = "Other data",
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns game.
     *
     * @return game
     */
    fun getGame(): Game {
        return Game(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            mediaCount = TestConstants.MEDIA,
            format = TestConstants.FORMAT,
            crack = true,
            serialKey = true,
            patch = true,
            trainer = true,
            trainerData = true,
            editor = true,
            saves = true,
            otherData = "Other data",
            note = TestConstants.NOTE,
            cheat = true
        )
    }

    /**
     * Asserts game deep equals.
     *
     * @param expected expected game
     * @param actual   actual FO for game
     */
    fun assertGameDeepEquals(expected: Game, actual: GameFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.mediaCount).isEqualTo(expected.mediaCount.toString())
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.crack).isEqualTo(expected.crack)
            it.assertThat(actual.serialKey).isEqualTo(expected.serialKey)
            it.assertThat(actual.patch).isEqualTo(expected.patch)
            it.assertThat(actual.trainer).isEqualTo(expected.trainer)
            it.assertThat(actual.trainerData).isEqualTo(expected.trainerData)
            it.assertThat(actual.editor).isEqualTo(expected.editor)
            it.assertThat(actual.saves).isEqualTo(expected.saves)
            it.assertThat(actual.otherData).isEqualTo(expected.otherData)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

    /**
     * Asserts FO for game and request deep equals.
     *
     * @param expected expected FO for game
     * @param actual   actual request for changing game
     */
    fun assertRequestDeepEquals(expected: GameFO, actual: ChangeGameRequest) {
        assertSoftly {
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.mediaCount).isEqualTo(expected.mediaCount!!.toInt())
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.crack).isEqualTo(expected.crack)
            it.assertThat(actual.serialKey).isEqualTo(expected.serialKey)
            it.assertThat(actual.patch).isEqualTo(expected.patch)
            it.assertThat(actual.trainer).isEqualTo(expected.trainer)
            it.assertThat(actual.trainerData).isEqualTo(expected.trainerData)
            it.assertThat(actual.editor).isEqualTo(expected.editor)
            it.assertThat(actual.saves).isEqualTo(expected.saves)
            it.assertThat(actual.otherData).isEqualTo(expected.otherData)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

}
