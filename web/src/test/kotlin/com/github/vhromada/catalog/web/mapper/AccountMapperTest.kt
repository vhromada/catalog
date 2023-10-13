package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.AccountUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for accounts.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class AccountMapperTest {

    /**
     * Instance of [AccountMapper]
     */
    @Autowired
    private lateinit var mapper: AccountMapper

    /**
     * Test method for [AccountMapper.map].
     */
    @Test
    fun map() {
        val account = AccountUtils.getAccount()

        val result = mapper.map(source = account)

        AccountUtils.assertAccountDeepEquals(expected = account, actual = result)
    }

    /**
     * Test method for [AccountMapper.mapFilter].
     */
    @Test
    fun mapFilter() {
        val filter = AccountUtils.getFilter()

        val result = mapper.mapFilter(source = filter)

        AccountUtils.assertAccountFilterDeepEquals(expected = filter, actual = result)
    }

}
