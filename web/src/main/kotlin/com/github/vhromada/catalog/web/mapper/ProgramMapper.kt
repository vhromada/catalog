package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeProgramRequest
import com.github.vhromada.catalog.web.connector.entity.Program
import com.github.vhromada.catalog.web.fo.ProgramFO

/**
 * An interface represents mapper for programs.
 *
 * @author Vladimir Hromada
 */
interface ProgramMapper {

    /**
     * Maps program.
     *
     * @param source program
     * @return mapped FO for program
     */
    fun map(source: Program): ProgramFO

    /**
     * Maps FO for program.
     *
     * @param source FO for program
     * @return mapped request for changing program
     */
    fun mapRequest(source: ProgramFO): ChangeProgramRequest

}
