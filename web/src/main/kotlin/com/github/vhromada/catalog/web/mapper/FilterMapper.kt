package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.filter.MultipleNameFilter
import com.github.vhromada.catalog.web.connector.filter.NameFilter
import com.github.vhromada.catalog.web.fo.MultipleNameFilterFO
import com.github.vhromada.catalog.web.fo.NameFilterFO

/**
 * An interface represents mapper for filters.
 *
 * @author Vladimir Hromada
 */
interface FilterMapper {

    /**
     * Maps filter for names.
     *
     * @param source FO for filter for names
     * @return mapped filter for names
     */
    fun mapNameFilter(source: NameFilterFO): NameFilter

    /**
     * Maps filter for multiple names.
     *
     * @param source FO for filter for multiple names
     * @return mapped filter for multiple names
     */
    fun mapMultipleNameFilter(source: MultipleNameFilterFO): MultipleNameFilter

}