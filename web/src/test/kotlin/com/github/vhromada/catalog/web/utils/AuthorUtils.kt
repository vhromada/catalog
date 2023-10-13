package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.Author
import com.github.vhromada.catalog.web.connector.entity.ChangeAuthorRequest
import com.github.vhromada.catalog.web.connector.filter.AuthorFilter
import com.github.vhromada.catalog.web.fo.AuthorFO
import com.github.vhromada.catalog.web.fo.AuthorFilterFO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for authors.
 *
 * @author Vladimir Hromada
 */
object AuthorUtils {

    /**
     * Returns FO for author.
     *
     * @return FO for author
     */
    fun getAuthorFO(): AuthorFO {
        return AuthorFO(
            uuid = TestConstants.UUID,
            firstName = "First name",
            middleName = "Middle name",
            lastName = "Last name"
        )
    }

    /**
     * Returns author.
     *
     * @return author
     */
    fun getAuthor(): Author {
        return Author(
            uuid = TestConstants.UUID,
            firstName = "First name",
            middleName = "Middle name",
            lastName = "Last name"
        )
    }

    /**
     * Returns FO for filter for authors.
     *
     * @return FO for filter for authors
     */
    fun getFilter(): AuthorFilterFO {
        return AuthorFilterFO(
            firstName = "First name",
            middleName = "Middle name",
            lastName = "Last name"
        )
    }

    /**
     * Asserts author deep equals.
     *
     * @param expected expected author
     * @param actual   actual FO for author
     */
    fun assertAuthorDeepEquals(expected: Author, actual: AuthorFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.firstName).isEqualTo(expected.firstName)
            it.assertThat(actual.middleName).isEqualTo(expected.middleName)
            it.assertThat(actual.lastName).isEqualTo(expected.lastName)
        }
    }

    /**
     * Asserts FO for author and request deep equals.
     *
     * @param expected expected FO for author
     * @param actual   actual request for changing author
     */
    fun assertRequestDeepEquals(expected: AuthorFO, actual: ChangeAuthorRequest) {
        assertSoftly {
            it.assertThat(actual.firstName).isEqualTo(expected.firstName)
            it.assertThat(actual.middleName).isEqualTo(expected.middleName)
            it.assertThat(actual.lastName).isEqualTo(expected.lastName)
        }
    }

    /**
     * Asserts list of authors deep equals.
     *
     * @param expected expected list of authors
     * @param actual   actual list of authors
     */
    fun assertAuthorsDeepEquals(expected: List<Author>, actual: List<String?>?) {
        assertThat(actual).isNotNull
        assertThat(actual!!.size).isEqualTo(expected.size)
        for (i in expected.indices) {
            assertThat(actual[i]).isEqualTo(expected[i].uuid)
        }
    }

    /**
     * Asserts filter for authors deep equals.
     *
     * @param expected expected FO for filter for authors
     * @param actual   actual filter for authors
     */
    fun assertAuthorFilterDeepEquals(expected: AuthorFilterFO, actual: AuthorFilter) {
        assertSoftly {
            it.assertThat(actual.firstName).isEqualTo(expected.firstName)
            it.assertThat(actual.middleName).isEqualTo(expected.middleName)
            it.assertThat(actual.lastName).isEqualTo(expected.lastName)
        }
    }

}
