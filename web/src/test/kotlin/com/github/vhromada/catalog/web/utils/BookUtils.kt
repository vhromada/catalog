package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.Book
import com.github.vhromada.catalog.web.connector.entity.ChangeBookRequest
import com.github.vhromada.catalog.web.fo.BookFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for books.
 *
 * @author Vladimir Hromada
 */
object BookUtils {

    /**
     * Returns FO for book.
     *
     * @return FO for book
     */
    fun getBookFO(): BookFO {
        return BookFO(
            uuid = TestConstants.UUID,
            czechName = TestConstants.CZECH_NAME,
            originalName = TestConstants.ORIGINAL_NAME,
            description = "Description",
            note = TestConstants.NOTE,
            authors = listOf(TestConstants.UUID)
        )
    }

    /**
     * Returns book.
     *
     * @return book
     */
    fun getBook(): Book {
        return Book(
            uuid = TestConstants.UUID,
            czechName = "czName",
            originalName = "origName",
            description = "Description",
            note = TestConstants.NOTE,
            authors = listOf(AuthorUtils.getAuthor()),
            itemsCount = 1
        )
    }

    /**
     * Asserts book deep equals.
     *
     * @param expected expected book
     * @param actual   actual FO for book
     */
    fun assertBookDeepEquals(expected: Book, actual: BookFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.description).isEqualTo(expected.description)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
        AuthorUtils.assertAuthorsDeepEquals(expected = expected.authors, actual = actual.authors)
    }

    /**
     * Asserts FO for book and request deep equals.
     *
     * @param expected expected FO for book
     * @param actual   actual request for changing book
     */
    fun assertRequestDeepEquals(expected: BookFO, actual: ChangeBookRequest) {
        assertSoftly {
            it.assertThat(actual.czechName).isEqualTo(expected.czechName)
            it.assertThat(actual.originalName).isEqualTo(expected.originalName)
            it.assertThat(actual.description).isEqualTo(expected.description)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.authors).isEqualTo(expected.authors)
        }
    }

}
