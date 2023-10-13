package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.RoleUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for roles.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class RoleMapperTest {

    /**
     * Instance of [RoleMapper]
     */
    @Autowired
    private lateinit var mapper: RoleMapper

    /**
     * Test method for [RoleMapper.map].
     */
    @Test
    fun mapRequest() {
        val roleFO = RoleUtils.getRole()

        val result = mapper.map(source = roleFO)

        RoleUtils.assertRequestDeepEquals(expected = roleFO, actual = result)
    }

}
