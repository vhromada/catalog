package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.AuthorUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for authors.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class AuthorMapperTest {

    /**
     * Instance of [AuthorMapper]
     */
    @Autowired
    private lateinit var mapper: AuthorMapper

    /**
     * Test method for [AuthorMapper.map].
     */
    @Test
    fun map() {
        val author = AuthorUtils.getAuthor()

        val result = mapper.map(source = author)

        AuthorUtils.assertAuthorDeepEquals(expected = author, actual = result)
    }

    /**
     * Test method for [AuthorMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val authorFO = AuthorUtils.getAuthorFO()

        val result = mapper.mapRequest(source = authorFO)

        AuthorUtils.assertRequestDeepEquals(expected = authorFO, actual = result)
    }

    /**
     * Test method for [AuthorMapper.mapFilter].
     */
    @Test
    fun mapFilter() {
        val filter = AuthorUtils.getFilter()

        val result = mapper.mapFilter(source = filter)

        AuthorUtils.assertAuthorFilterDeepEquals(expected = filter, actual = result)
    }

}
