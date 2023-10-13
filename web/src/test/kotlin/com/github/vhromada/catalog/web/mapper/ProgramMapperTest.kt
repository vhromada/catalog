package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.CatalogWebMapperTestConfiguration
import com.github.vhromada.catalog.web.utils.ProgramUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper for programs.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogWebMapperTestConfiguration::class])
class ProgramMapperTest {

    /**
     * Instance of [ProgramMapper]
     */
    @Autowired
    private lateinit var mapper: ProgramMapper

    /**
     * Test method for [ProgramMapper.map].
     */
    @Test
    fun map() {
        val program = ProgramUtils.getProgram()

        val result = mapper.map(source = program)

        ProgramUtils.assertProgramDeepEquals(expected = program, actual = result)
    }

    /**
     * Test method for [ProgramMapper.mapRequest].
     */
    @Test
    fun mapRequest() {
        val programFO = ProgramUtils.getProgramFO()

        val result = mapper.mapRequest(source = programFO)

        ProgramUtils.assertRequestDeepEquals(expected = programFO, actual = result)
    }

}
