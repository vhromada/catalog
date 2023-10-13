package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.BookItemUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for book items.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class BookItemMapperTest {

    /**
     * Instance of [BookItemMapper]
     */
    @Autowired
    private lateinit var mapper: BookItemMapper

    /**
     * Test method for [BookItemMapper.map].
     */
    @Test
    fun map() {
        val bookItem = BookItemUtils.getBookItem()

        val result = mapper.map(source = bookItem)

        BookItemUtils.assertBookItemDeepEquals(expected = bookItem, actual = result)
    }

    /**
     * Test method for [BookItemMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val bookItemFO = BookItemUtils.getBookItemFO()

        val result = mapper.mapRequest(source = bookItemFO)

        BookItemUtils.assertRequestDeepEquals(expected = bookItemFO, actual = result)
    }

}
