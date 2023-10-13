package com.github.vhromada.catalog.web.validator

import com.github.vhromada.catalog.web.fo.SeasonFO
import com.github.vhromada.catalog.web.validator.constraints.Years
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.util.regex.Pattern

/**
 * A class represents validator for years constraint.
 *
 * @author Vladimir Hromada
 */
class YearsValidator : ConstraintValidator<Years, SeasonFO> {

    override fun isValid(value: SeasonFO?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        val startYear = value.startYear
        val endYear = value.endYear
        if (isNotStringValid(startYear) || isNotStringValid(endYear)) {
            return true
        }

        val startYearValue = startYear!!.toInt()
        val endYearValue = endYear!!.toInt()
        return if (isNotIntValid(startYearValue) || isNotIntValid(endYearValue)) {
            true
        } else {
            startYearValue <= endYearValue
        }
    }

    /**
     * Validates year as string.
     *
     * @param value value to validate
     * @return true if value isn't null and is valid integer
     */
    private fun isNotStringValid(value: String?): Boolean {
        return value == null || !PATTERN.matcher(value).matches()
    }

    /**
     * Validates year as integer.
     *
     * @param value value to validate
     * @return true if value is in valid range
     */
    private fun isNotIntValid(value: Int): Boolean {
        return value < MIN_YEAR || value > MAX_YEAR
    }

    companion object {

        /**
         * Minimal year
         */
        private const val MIN_YEAR = 1930

        /**
         * Current year
         */
        private val MAX_YEAR = LocalDate.now().year

        /**
         * Year pattern
         */
        private val PATTERN = Pattern.compile("\\d{4}")

    }

}
