package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.common.entity.Time
import com.github.vhromada.catalog.web.fo.TimeFO
import org.assertj.core.api.Assertions.assertThat

/**
 * A class represents utility class for time.
 *
 * @author Vladimir Hromada
 */
object TimeUtils {

    /**
     * Returns FO for time.
     *
     * @return FO for time
     */
    fun getTimeFO(): TimeFO {
        return TimeFO(
            hours = "1",
            minutes = "2",
            seconds = "3"
        )
    }

    /**
     * Asserts time deep equals.
     *
     * @param expected expected time
     * @param actual   actual length
     */
    fun assertTimeDeepEquals(expected: TimeFO?, actual: Int?) {
        if (expected == null) {
            assertThat(actual).isNull()
        } else {
            val actualTime = Time(length = actual!!)

            assertThat(actualTime.getData(dataType = Time.TimeData.HOUR)).isEqualTo(expected.hours!!.toInt())
            assertThat(actualTime.getData(dataType = Time.TimeData.MINUTE)).isEqualTo(expected.minutes!!.toInt())
            assertThat(actualTime.getData(dataType = Time.TimeData.SECOND)).isEqualTo(expected.seconds!!.toInt())
        }
    }

    /**
     * Asserts time deep equals.
     *
     * @param expected expected length
     * @param actual   actual time
     */
    fun assertTimeDeepEquals(expected: Int?, actual: TimeFO?) {
        if (expected == null) {
            assertThat(actual).isNull()
        } else {
            val expectedTime = Time(length = expected)

            assertThat(actual!!.hours).isEqualTo(expectedTime.getData(dataType = Time.TimeData.HOUR).toString())
            assertThat(actual.minutes).isEqualTo(expectedTime.getData(dataType = Time.TimeData.MINUTE).toString())
            assertThat(actual.seconds).isEqualTo(expectedTime.getData(dataType = Time.TimeData.SECOND).toString())
        }
    }

}
