package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeSeasonRequest
import com.github.vhromada.catalog.web.connector.entity.Season
import com.github.vhromada.catalog.web.fo.SeasonFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for seasons.
 *
 * @author Vladimir Hromada
 */
object SeasonUtils {

    /**
     * Returns FO for season.
     *
     * @return FO for season
     */
    fun getSeasonFO(): SeasonFO {
        return SeasonFO(
            uuid = TestConstants.UUID,
            number = TestConstants.NUMBER.toString(),
            startYear = TestConstants.YEAR.toString(),
            endYear = (TestConstants.YEAR + 1).toString(),
            language = TestConstants.LANGUAGE,
            subtitles = listOf(TestConstants.LANGUAGE),
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns season.
     *
     * @return season
     */
    fun getSeason(): Season {
        return Season(
            uuid = TestConstants.UUID,
            number = TestConstants.NUMBER,
            startYear = TestConstants.YEAR,
            endYear = TestConstants.YEAR + 1,
            language = TestConstants.LANGUAGE,
            subtitles = listOf(TestConstants.LANGUAGE),
            note = TestConstants.NOTE,
            episodesCount = 1,
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH
        )
    }

    /**
     * Asserts season deep equals.
     *
     * @param expected expected season
     * @param actual   actual FO for season
     */
    fun assertSeasonDeepEquals(expected: Season, actual: SeasonFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.number).isEqualTo(expected.number.toString())
            it.assertThat(actual.startYear).isEqualTo(expected.startYear.toString())
            it.assertThat(actual.endYear).isEqualTo(expected.endYear.toString())
            it.assertThat(actual.language).isEqualTo(expected.language)
            it.assertThat(actual.subtitles).isEqualTo(expected.subtitles)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

    /**
     * Asserts FO for season and request deep equals.
     *
     * @param expected expected FO for season
     * @param actual   actual request for changing season
     */
    fun assertRequestDeepEquals(expected: SeasonFO, actual: ChangeSeasonRequest) {
        assertSoftly {
            it.assertThat(actual.number).isEqualTo(expected.number!!.toInt())
            it.assertThat(actual.startYear).isEqualTo(expected.startYear!!.toInt())
            it.assertThat(actual.endYear).isEqualTo(expected.endYear!!.toInt())
            it.assertThat(actual.language).isEqualTo(expected.language)
            it.assertThat(actual.subtitles).isEqualTo(expected.subtitles)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

}
