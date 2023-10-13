package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeGenreRequest
import com.github.vhromada.catalog.web.connector.entity.Genre
import com.github.vhromada.catalog.web.fo.GenreFO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for genres.
 *
 * @author Vladimir Hromada
 */
object GenreUtils {

    /**
     * Returns FO for genre.
     *
     * @return FO for genre
     */
    fun getGenreFO(): GenreFO {
        return GenreFO(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME
        )
    }

    /**
     * Returns genre.
     *
     * @return genre
     */
    fun getGenre(): Genre {
        return Genre(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME
        )
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected genre
     * @param actual   actual FO for genre
     */
    fun assertGenreDeepEquals(expected: Genre, actual: GenreFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.name).isEqualTo(expected.name)
        }
    }

    /**
     * Asserts FO for genre and request deep equals.
     *
     * @param expected expected FO for genre
     * @param actual   actual request for changing genre
     */
    fun assertRequestDeepEquals(expected: GenreFO, actual: ChangeGenreRequest) {
        assertThat(actual.name).isEqualTo(expected.name)
    }

    /**
     * Asserts list of genres deep equals.
     *
     * @param expected expected list of genres
     * @param actual   actual list of genres
     */
    fun assertGenresDeepEquals(expected: List<Genre>, actual: List<String?>?) {
        assertThat(actual).isNotNull
        assertThat(actual!!.size).isEqualTo(expected.size)
        for (i in expected.indices) {
            assertThat(actual[i]).isEqualTo(expected[i].uuid)
        }
    }

}
