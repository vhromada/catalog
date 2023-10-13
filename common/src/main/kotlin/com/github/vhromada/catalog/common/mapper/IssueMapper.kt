package com.github.vhromada.catalog.common.mapper

import com.github.vhromada.catalog.common.entity.IssueList
import com.github.vhromada.catalog.common.result.Result

/**
 * An interface represents mapper between result and issues.
 *
 * @author Vladimir Hromada
 */
interface IssueMapper {

    /**
     * Maps result to issues.
     *
     * @param source result
     * @return mapped issues
     */
    fun map(source: Result<*>): IssueList

}
