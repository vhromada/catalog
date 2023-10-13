package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeMusicRequest
import com.github.vhromada.catalog.web.connector.entity.Music
import com.github.vhromada.catalog.web.fo.MusicFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for music.
 *
 * @author Vladimir Hromada
 */
object MusicUtils {

    /**
     * Returns FO for music.
     *
     * @return FO for music
     */
    fun getMusicFO(): MusicFO {
        return MusicFO(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            mediaCount = TestConstants.MEDIA.toString(),
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns music.
     *
     * @return music
     */
    fun getMusic(): Music {
        return Music(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            mediaCount = TestConstants.MEDIA,
            note = TestConstants.NOTE,
            songsCount = 1,
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH
        )
    }

    /**
     * Asserts music deep equals.
     *
     * @param expected expected music
     * @param actual   actual FO for music
     */
    fun assertMusicDeepEquals(expected: Music, actual: MusicFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.mediaCount).isEqualTo(expected.mediaCount.toString())
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

    /**
     * Asserts FO for music and request deep equals.
     *
     * @param expected expected FO for music
     * @param actual   actual request for changing music
     */
    fun assertRequestDeepEquals(expected: MusicFO, actual: ChangeMusicRequest) {
        assertSoftly {
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.mediaCount).isEqualTo(expected.mediaCount!!.toInt())
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

}
