package com.github.vhromada.catalog.web.validator

import com.github.vhromada.catalog.web.validator.constraints.DateRange
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.util.regex.Pattern

/**
 * A class represents validator for date range constraint.
 *
 * @author Vladimir Hromada
 */
class DateRangeValidator : ConstraintValidator<DateRange, String> {

    override fun isValid(date: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (date == null || !PATTERN.matcher(date).matches()) {
            return false
        }

        return date.toInt() in MIN_YEAR..MAX_YEAR
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
         * Date pattern
         */
        private val PATTERN = Pattern.compile("\\d{4}")

    }

}
