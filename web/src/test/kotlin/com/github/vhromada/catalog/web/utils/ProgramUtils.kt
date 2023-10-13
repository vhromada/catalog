package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeProgramRequest
import com.github.vhromada.catalog.web.connector.entity.Program
import com.github.vhromada.catalog.web.fo.ProgramFO
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for programs.
 *
 * @author Vladimir Hromada
 */
object ProgramUtils {

    /**
     * Returns FO for program.
     *
     * @return FO for program
     */
    fun getProgramFO(): ProgramFO {
        return ProgramFO(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            mediaCount = TestConstants.MEDIA.toString(),
            format = TestConstants.FORMAT,
            crack = true,
            serialKey = true,
            otherData = "Other data",
            note = TestConstants.NOTE
        )
    }

    /**
     * Returns program.
     *
     * @return program
     */
    fun getProgram(): Program {
        return Program(
            uuid = TestConstants.UUID,
            name = TestConstants.NAME,
            wikiEn = TestConstants.EN_WIKI,
            wikiCz = TestConstants.CZ_WIKI,
            mediaCount = TestConstants.MEDIA,
            format = TestConstants.FORMAT,
            crack = true,
            serialKey = true,
            otherData = "Other data",
            note = TestConstants.NOTE
        )
    }

    /**
     * Asserts program deep equals.
     *
     * @param expected expected program
     * @param actual   actual FO for program
     */
    fun assertProgramDeepEquals(expected: Program, actual: ProgramFO) {
        assertSoftly {
            it.assertThat(actual.uuid).isEqualTo(expected.uuid)
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.mediaCount).isEqualTo(expected.mediaCount.toString())
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.crack).isEqualTo(expected.crack)
            it.assertThat(actual.serialKey).isEqualTo(expected.serialKey)
            it.assertThat(actual.otherData).isEqualTo(expected.otherData)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

    /**
     * Asserts FO for program and request deep equals.
     *
     * @param expected expected FO for program
     * @param actual   actual request for changing program
     */
    fun assertRequestDeepEquals(expected: ProgramFO, actual: ChangeProgramRequest) {
        assertSoftly {
            it.assertThat(actual.name).isEqualTo(expected.name)
            it.assertThat(actual.wikiEn).isEqualTo(expected.wikiEn)
            it.assertThat(actual.wikiCz).isEqualTo(expected.wikiCz)
            it.assertThat(actual.mediaCount).isEqualTo(expected.mediaCount!!.toInt())
            it.assertThat(actual.format).isEqualTo(expected.format)
            it.assertThat(actual.crack).isEqualTo(expected.crack)
            it.assertThat(actual.serialKey).isEqualTo(expected.serialKey)
            it.assertThat(actual.otherData).isEqualTo(expected.otherData)
            it.assertThat(actual.note).isEqualTo(expected.note)
        }
    }

}
