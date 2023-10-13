package com.github.vhromada.catalog.web.validator

import com.github.vhromada.catalog.web.validator.constraints.ImdbCode
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Pattern

/**
 * A class represents validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
class ImdbCodeValidator : ConstraintValidator<ImdbCode, String> {

    override fun isValid(imdbCode: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (imdbCode.isNullOrEmpty()) {
            return true
        }
        if (!PATTERN.matcher(imdbCode).matches()) {
            return false
        }

        return imdbCode.toInt() in 1..MAX_IMDB_CODE
    }

    companion object {

        /**
         * Maximum IMDB code
         */
        const val MAX_IMDB_CODE = 999_999_999

        /**
         * IMDB code pattern
         */
        private val PATTERN = Pattern.compile("\\d{1,9}")

    }

}
