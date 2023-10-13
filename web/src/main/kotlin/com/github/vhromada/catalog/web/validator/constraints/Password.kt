package com.github.vhromada.catalog.web.validator.constraints

import com.github.vhromada.catalog.web.validator.PasswordValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * An annotation represents password constraint.
 *
 * @author Vladimir Hromada
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordValidator::class])
@MustBeDocumented
annotation class Password(
    /**
     * Returns error message template.
     *
     * @return error message template
     */
    val message: String = "{com.github.vhromada.catalog.web.validator.constraints.Password.message}",

    /**
     * Returns groups constraint belongs to.
     *
     * @return groups constraint belongs to
     */
    val groups: Array<KClass<*>> = [],

    /**
     * Returns payload associated to constraint.
     *
     * @return payload associated to constraint
     */
    val payload: Array<KClass<out Payload>> = []
)
