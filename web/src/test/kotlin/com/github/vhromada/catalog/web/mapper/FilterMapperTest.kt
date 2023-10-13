package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.fo.MultipleNameFilterFO
import com.github.vhromada.catalog.web.fo.NameFilterFO
import com.github.vhromada.catalog.web.utils.TestConstants
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for filters.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class FilterMapperTest {

    /**
     * Instance of [FilterMapper]
     */
    @Autowired
    private lateinit var mapper: FilterMapper

    /**
     * Test method for [FilterMapper.mapNameFilter].
     */
    @Test
    fun mapNameFilter() {
        val filter = NameFilterFO(name = TestConstants.NAME)

        val result = mapper.mapNameFilter(source = filter)

        assertSoftly {
            it.assertThat(result.name).isEqualTo(filter.name)
            it.assertThat(result.page).isNull()
            it.assertThat(result.limit).isNull()
        }
    }

    /**
     * Test method for [FilterMapper.mapMultipleNameFilter].
     */
    @Test
    fun mapMultipleNameFilter() {
        val filter = MultipleNameFilterFO(
            czechName = TestConstants.CZECH_NAME,
            originalName = TestConstants.ORIGINAL_NAME
        )

        val result = mapper.mapMultipleNameFilter(source = filter)

        assertSoftly {
            it.assertThat(result.czechName).isEqualTo(filter.czechName)
            it.assertThat(result.originalName).isEqualTo(filter.originalName)
            it.assertThat(result.page).isNull()
            it.assertThat(result.limit).isNull()
        }
    }

}
