package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.BookItem
import com.github.vhromada.catalog.web.connector.entity.ChangeBookItemRequest
import com.github.vhromada.catalog.web.fo.BookItemFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for book items.
 *
 * @author Vladimir Hromada
 */
object BookItemUtils {

    /**
     * Returns FO for book item.
     *
     * @return FO for book item
     */
    fun getBookItemFO(): BookItemFO {
        return BookItemFO(
            uuid = TestConstants.UUID,
            languages = listOf(TestConstants.LANGUAGE),
            format = TestConstants.FORMAT,
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns book item.
     *
     * @return book item
     */
    fun getBookItem(): BookItem {
        return BookItem(
            uuid = TestConstants.UUID,
            languages = listOf(TestConstants.LANGUAGE),
            format = TestConstants.FORMAT,
            note = TestConstants.NOTE
        )
    }

    /**
     * Asserts book item deep equals.
     *
     * @param expected expected book item
     * @param actual   actual FO for book item
     */
    fun assertBookItemDeepEquals(expected: BookItem, actual: BookItemFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.languages).isEqualTo(expected.languages)
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

    /**
     * Asserts FO for book item and request deep equals.
     *
     * @param expected expected FO for book item
     * @param actual   actual request for changing book item
     */
    fun assertRequestDeepEquals(expected: BookItemFO, actual: ChangeBookItemRequest) {
        assertSoftly {
            it.assertThat(actual.languages).isEqualTo(expected.languages)
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

}
