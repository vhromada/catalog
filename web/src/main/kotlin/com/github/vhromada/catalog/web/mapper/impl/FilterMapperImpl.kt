package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.filter.MultipleNameFilter
import com.github.vhromada.catalog.web.connector.filter.NameFilter
import com.github.vhromada.catalog.web.fo.MultipleNameFilterFO
import com.github.vhromada.catalog.web.fo.NameFilterFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for filters.
 *
 * @author Vladimir Hromada
 */
@Component("filterMapper")
class FilterMapperImpl : FilterMapper {

    override fun mapNameFilter(source: NameFilterFO): NameFilter {
        return NameFilter(name = source.name)
    }

    override fun mapMultipleNameFilter(source: MultipleNameFilterFO): MultipleNameFilter {
        return MultipleNameFilter(
            czechName = source.czechName,
            originalName = source.originalName
        )
    }

}
