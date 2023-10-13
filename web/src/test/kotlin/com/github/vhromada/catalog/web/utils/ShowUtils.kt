package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeShowRequest
import com.github.vhromada.catalog.web.connector.entity.Show
import com.github.vhromada.catalog.web.fo.ShowFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for shows.
 *
 * @author Vladimir Hromada
 */
object ShowUtils {

    /**
     * Returns FO for show.
     *
     * @return FO for show
     */
    fun getShowFO(): ShowFO {
        return ShowFO(
            uuid = TestConstants.UUID,
            czechName = TestConstants.CZECH_NAME,
            originalName = TestConstants.ORIGINAL_NAME,
            csfd = "Csfd",
            imdb = true,
            imdbCode = "1000",
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            picture = TestConstants.UUID,
            note = TestConstants.NOTE,
            genres = listOf(TestConstants.UUID)
        )
    }

    /**
     * Returns show.
     *
     * @return show
     */
    fun getShow(): Show {
        return Show(
            uuid = TestConstants.UUID,
            czechName = "czName",
            originalName = "origName",
            csfd = "Csfd",
            imdbCode = 1000,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            picture = TestConstants.UUID,
            note = TestConstants.NOTE,
            genres = listOf(GenreUtils.getGenre()),
            seasonsCount = 1,
            episodesCount = 2,
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH
        )
    }

    /**
     * Asserts show deep equals.
     *
     * @param expected expected show
     * @param actual   actual FO for show
     */
    fun assertShowDeepEquals(expected: Show, actual: ShowFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.csfd).isEqualTo(expected.csfd)
            ImdbUtils.assertImdbDeepEquals(softly = it, expectedImdbCode = expected.imdbCode!!, actualImdb = actual.imdb, actualImdbCode = actual.imdbCode)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.picture).isEqualTo(expected.picture)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        GenreUtils.assertGenresDeepEquals(expected = expected.genres, actual = actual.genres)
    }

    /**
     * Asserts FO for show and request deep equals.
     *
     * @param expected expected FO for show
     * @param actual   actual request for changing show
     */
    fun assertRequestDeepEquals(expected: ShowFO, actual: ChangeShowRequest) {
        assertSoftly {
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.csfd).isEqualTo(expected.csfd)
            ImdbUtils.assertImdbCodeDeepEquals(softly = it, expectedImdb = expected.imdb, expectedImdbCode = expected.imdbCode, actualImdbCode = actual.imdbCode)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.picture).isEqualTo(expected.picture)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.genres).isEqualTo(expected.genres)
        }
    }

}
