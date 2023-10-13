package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.BookUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for books.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class BookMapperTest {

    /**
     * Instance of [BookMapper]
     */
    @Autowired
    private lateinit var mapper: BookMapper

    /**
     * Test method for [BookMapper.map].
     */
    @Test
    fun map() {
        val book = BookUtils.getBook()

        val result = mapper.map(source = book)

        BookUtils.assertBookDeepEquals(expected = book, actual = result)
    }

    /**
     * Test method for [BookMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val bookFO = BookUtils.getBookFO()

        val result = mapper.mapRequest(source = bookFO)

        BookUtils.assertRequestDeepEquals(expected = bookFO, actual = result)
    }

}
