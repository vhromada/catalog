package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeSongRequest
import com.github.vhromada.catalog.web.connector.entity.Song
import com.github.vhromada.catalog.web.fo.SongFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for songs.
 *
 * @author Vladimir Hromada
 */
object SongUtils {

    /**
     * Returns FO for song.
     *
     * @return FO for song
     */
    fun getSongFO(): SongFO {
        return SongFO(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            length = TimeUtils.getTimeFO(),
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns song.
     *
     * @return song
     */
    fun getSong(): Song {
        return Song(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH,
            note = TestConstants.NOTE
        )
    }

    /**
     * Asserts song deep equals.
     *
     * @param expected expected song
     * @param actual   actual FO for song
     */
    fun assertSongDeepEquals(expected: Song, actual: SongFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        TimeUtils.assertTimeDeepEquals(expected = expected.length, actual = actual.length)
    }

    /**
     * Asserts FO for song and request deep equals.
     *
     * @param expected expected FO for song
     * @param actual   actual request for changing song
     */
    fun assertRequestDeepEquals(expected: SongFO, actual: ChangeSongRequest) {
        assertSoftly {
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        TimeUtils.assertTimeDeepEquals(expected = expected.length, actual = actual.length)
    }

}
