package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeProgramRequest
import com.github.vhromada.catalog.web.connector.entity.Program
import com.github.vhromada.catalog.web.fo.ProgramFO
import com.github.vhromada.catalog.web.mapper.ProgramMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for programs.
 *
 * @author Vladimir Hromada
 */
@Component("programMapper")
class ProgramMapperImpl : ProgramMapper {

    override fun map(source: Program): ProgramFO {
        return ProgramFO(
            uuid = source.uuid,
            name = source.name,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            mediaCount = source.mediaCount.toString(),
            format = source.format,
            crack = source.crack,
            serialKey = source.serialKey,
            otherData = source.otherData,
            note = source.note
        )
    }

    override fun mapRequest(source: ProgramFO): ChangeProgramRequest {
        return ChangeProgramRequest(
            name = source.name!!,
            wikiEn = source.wikiEn,
            wikiCz = source.wikiCz,
            mediaCount = source.mediaCount!!.toInt(),
            format = source.format!!,
            crack = source.crack!!,
            serialKey = source.serialKey!!,
            otherData = source.otherData,
            note = source.note
        )
    }

}
