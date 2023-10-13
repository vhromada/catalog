package com.github.vhromada.catalog.web.connector

import com.github.vhromada.catalog.common.entity.Page
import com.github.vhromada.catalog.web.connector.entity.ChangeProgramRequest
import com.github.vhromada.catalog.web.connector.entity.Program
import com.github.vhromada.catalog.web.connector.entity.ProgramStatistics
import com.github.vhromada.catalog.web.connector.filter.NameFilter

/**
 * An interface represents connector for programs.
 *
 * @author Vladimir Hromada
 */
interface ProgramConnector {

    /**
     * Returns list of programs for filter.
     *
     * @param filter filter
     * @return list of programs for filter
     */
    fun search(filter: NameFilter): Page<Program>

    /**
     * Returns program.
     *
     * @param uuid UUID
     * @return program
     */
    fun get(uuid: String): Program

    /**
     * Adds program.
     *
     * @param request request for changing program
     * @return created program
     */
    fun add(request: ChangeProgramRequest): Program

    /**
     * Updates program.
     *
     * @param uuid    UUID
     * @param request request for changing program
     * @return updated program
     */
    fun update(uuid: String, request: ChangeProgramRequest): Program

    /**
     * Removes program.
     *
     * @param uuid UUID
     */
    fun remove(uuid: String)

    /**
     * Duplicates program.
     *
     * @param uuid UUID
     * @return created duplicated program
     */
    fun duplicate(uuid: String): Program

    /**
     * Returns statistics.
     *
     * @return Statistics
     */
    fun getStatistics(): ProgramStatistics

}
