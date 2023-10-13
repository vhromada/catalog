package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeMovieRequest
import com.github.vhromada.catalog.web.connector.entity.Medium
import com.github.vhromada.catalog.web.connector.entity.Movie
import com.github.vhromada.catalog.web.fo.MovieFO
import com.github.vhromada.catalog.web.fo.TimeFO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for movies.
 *
 * @author Vladimir Hromada
 */
object MovieUtils {

    /**
     * Returns FO for movie.
     *
     * @return FO for movie
     */
    fun getMovieFO(): MovieFO {
        return MovieFO(
            uuid = TestConstants.UUID,
            czechName = TestConstants.CZECH_NAME,
            originalName = TestConstants.ORIGINAL_NAME,
            year = TestConstants.YEAR.toString(),
            languages = listOf(TestConstants.LANGUAGE),
            subtitles = listOf(TestConstants.LANGUAGE),
            media = listOf(TimeUtils.getTimeFO()),
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
     * Returns movie.
     *
     * @return movie
     */
    fun getMovie(): Movie {
        val medium = Medium(
            number = TestConstants.NUMBER,
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH
        )

        return Movie(
            uuid = TestConstants.UUID,
            czechName = "czName",
            originalName = "origName",
            year = TestConstants.YEAR,
            languages = listOf(TestConstants.LANGUAGE),
            subtitles = listOf(TestConstants.LANGUAGE),
            media = listOf(medium),
            length = TestConstants.LENGTH,
            formattedLength = TestConstants.FORMATTED_LENGTH,
            csfd = "Csfd",
            imdbCode = 1000,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            picture = TestConstants.UUID,
            note = TestConstants.NOTE,
            genres = listOf(GenreUtils.getGenre())
        )
    }

    /**
     * Asserts movie deep equals.
     *
     * @param expected expected movie
     * @param actual   actual FO for movie
     */
    fun assertMovieDeepEquals(expected: Movie, actual: MovieFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.year).isEqualTo(expected.year.toString())
            it.assertThat(actual.languages).isEqualTo(expected.languages)
            it.assertThat(actual.subtitles).isEqualTo(expected.subtitles)
            it.assertThat(actual.csfd).isEqualTo(expected.csfd)
            ImdbUtils.assertImdbDeepEquals(softly = it, expectedImdbCode = expected.imdbCode!!, actualImdb = actual.imdb, actualImdbCode = actual.imdbCode)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.picture).isEqualTo(expected.picture)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        assertMediaDeepEquals(expected = expected.media, actual = actual.media)
        GenreUtils.assertGenresDeepEquals(expected = expected.genres, actual = actual.genres)
    }

    /**
     * Asserts media deep equals.
     *
     * @param expected expected list of medium
     * @param actual   actual list of FO for time
     */
    private fun assertMediaDeepEquals(expected: List<Medium>, actual: List<TimeFO?>?) {
        assertThat(actual).isNotNull
        assertThat(actual!!.size).isEqualTo(expected.size)
        for (i in expected.indices) {
            TimeUtils.assertTimeDeepEquals(expected = expected[i].length, actual = actual[i])
        }
    }

    /**
     * Asserts FO for movie and request deep equals.
     *
     * @param expected expected FO for movie
     * @param actual   actual request for changing movie
     */
    fun assertRequestDeepEquals(expected: MovieFO, actual: ChangeMovieRequest) {
        assertSoftly {
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.year).isEqualTo(expected.year!!.toInt())
            it.assertThat(actual.languages).isEqualTo(expected.languages)
            it.assertThat(actual.subtitles).isEqualTo(expected.subtitles)
            it.assertThat(actual.csfd).isEqualTo(expected.csfd)
            ImdbUtils.assertImdbCodeDeepEquals(softly = it, expectedImdb = expected.imdb, expectedImdbCode = expected.imdbCode, actualImdbCode = actual.imdbCode)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.picture).isEqualTo(expected.picture)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.genres).isEqualTo(expected.genres)
        }
        assertMediumListDeepEquals(expected = expected.media, actual = actual.media)
    }

    /**
     * Asserts media deep equals.
     *
     * @param expected expected list of FO for time
     * @param actual   actual list of medium
     */
    private fun assertMediumListDeepEquals(expected: List<TimeFO?>?, actual: List<Int>) {
        assertThat(actual.size).isEqualTo(expected!!.size)
        for (i in expected.indices) {
            TimeUtils.assertTimeDeepEquals(expected = expected[i], actual = actual[i])
        }
    }

}
