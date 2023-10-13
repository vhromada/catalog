package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeCheatData
import com.github.vhromada.catalog.web.connector.entity.CheatData
import com.github.vhromada.catalog.web.fo.CheatDataFO
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for cheat's data.
 *
 * @author Vladimir Hromada
 */
object CheatDataUtils {

    /**
     * Returns FO for cheat's data.
     *
     * @return FO for cheat's data
     */
    fun getCheatDataFO(): CheatDataFO {
        return CheatDataFO(
            action = "Action",
            description = "Description"
        )
    }

    /**
     * Returns cheat's data.
     *
     * @return cheat's data
     */
    fun getCheatData(): CheatData {
        return CheatData(
            action = "Action",
            description = "Description"
        )
    }

    /**
     * Asserts list of cheat's data deep equals.
     *
     * @param expected expected list of cheat's data
     * @param actual   actual list of FO for cheat's data
     */
    fun assertCheatDataListDeepEquals(expected: List<CheatData?>, actual: List<CheatDataFO?>?) {
        assertThat(actual).isNotNull
        assertThat(expected.size).isEqualTo(actual!!.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertCheatDataDeepEquals(expected = expected[i], actual = actual[i])
            }
        }
    }

    /**
     * Asserts cheat's data deep equals.
     *
     * @param expected expected cheat's data
     * @param actual   actual FO for cheat's data
     */
    fun assertCheatDataDeepEquals(expected: CheatData?, actual: CheatDataFO?) {
        if (expected == null) {
            assertThat(actual).isNull()
        } else {
            assertSoftly {
                it.assertThat(actual!!.action).isEqualTo(expected.action)
                it.assertThat(actual.description).isEqualTo(expected.description)
            }
        }
    }

    /**
     * Asserts list of FO for cheat's data and list of requests deep equals.
     *
     * @param expected expected list of FO for cheat's data
     * @param actual   actual list of changing cheat's data
     */
    fun assertRequestsDeepEquals(expected: List<CheatDataFO?>?, actual: List<ChangeCheatData>) {
        assertThat(expected!!.size).isEqualTo(actual.size)
        if (expected.isNotEmpty()) {
            for (i in expected.indices) {
                assertRequestDeepEquals(expected = expected[i]!!, actual = actual[i])
            }
        }
    }

    /**
     * Asserts FO for cheat's data and deep equals.
     *
     * @param expected expected FO for cheat's data
     * @param actual   actual changing cheat's data
     */
    fun assertRequestDeepEquals(expected: CheatDataFO, actual: ChangeCheatData) {
        assertSoftly {
            it.assertThat(actual.action).isEqualTo(expected.action)
            it.assertThat(actual.description).isEqualTo(expected.description)
        }
    }

}
