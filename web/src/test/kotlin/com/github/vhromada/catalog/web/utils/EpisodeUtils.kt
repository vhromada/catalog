package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeEpisodeRequest
import com.github.vhromada.catalog.web.connector.entity.Episode
import com.github.vhromada.catalog.web.fo.EpisodeFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for episodes.
 *
 * @author Vladimir Hromada
 */
object EpisodeUtils {

    /**
     * Returns FO for episode.
     *
     * @return FO for episode
     */
    fun getEpisodeFO(): EpisodeFO {
        return EpisodeFO(
            uuid = TestConstants.UUID,
            number = TestConstants.NUMBER.toString(),
            name = TestConstants.NAME,
            length = TimeUtils.getTimeFO(),
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns episode.
     *
     * @return episode
     */
    fun getEpisode(): Episode {
        return Episode(
            uuid = TestConstants.UUID,
            number = TestConstants.NUMBER,
            name = TestConstants.NAME,
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH,
            note = TestConstants.NOTE
        )
    }

    /**
     * Asserts episode deep equals.
     *
     * @param expected expected episode
     * @param actual   actual FO for episode
     */
    fun assertEpisodeDeepEquals(expected: Episode, actual: EpisodeFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.number).isEqualTo(expected.number.toString())
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        TimeUtils.assertTimeDeepEquals(expected = expected.length, actual = actual.length)
    }

    /**
     * Asserts FO for episode and request deep equals.
     *
     * @param expected expected FO for episode
     * @param actual   actual request for changing episode
     */
    fun assertRequestDeepEquals(expected: EpisodeFO, actual: ChangeEpisodeRequest) {
        assertSoftly {
            it.assertThat(actual.number).isEqualTo(expected.number!!.toInt())
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        TimeUtils.assertTimeDeepEquals(expected = expected.length, actual = actual.length)
    }

}
