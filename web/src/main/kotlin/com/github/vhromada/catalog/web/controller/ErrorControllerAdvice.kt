package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.common.exception.InputException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * A class represents advice for errors.
 *
 * @author Vladimir Hromada
 */
@ControllerAdvice
class ErrorControllerAdvice {

    /**
     * Logger
     */
    private val logger = KotlinLogging.logger {}

    /**
     * Shows page for illegal argument exception.
     *
     * @param model     model
     * @param exception exception
     * @return view for page for known exception
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun processKnownException(model: Model, exception: Exception): String {
        return processException(model = model, exception = exception, errorMessage = "There was error in working with data.")
    }

    /**
     * Shows page for input exception.
     *
     * @param model     model
     * @param exception exception
     * @return view for page for input exception
     */
    @ExceptionHandler(InputException::class)
    fun processInputException(model: Model, exception: Exception): String {
        return processException(model = model, exception = exception, errorMessage = "There was error in working with other systems.")
    }

    /**
     * Shows page for unknown exception.
     *
     * @param model     model
     * @param exception exception
     * @return view for page for unknown exception
     */
    @ExceptionHandler(Exception::class)
    fun processUnknownException(model: Model, exception: Exception): String {
        return processException(model = model, exception = exception, errorMessage = "There was unexpected error.")
    }

    /**
     * Process exception.
     *
     * @param model        model
     * @param exception    exception
     * @param errorMessage error message
     * @return view for page for exception
     */
    private fun processException(model: Model, exception: Exception, errorMessage: String): String {
        logger.error(exception) { "Web exception" }

        return process(model = model, errorMessage = errorMessage)
    }

    /**
     * Adds data to model and returns view for page for exception.
     *
     * @param model        model
     * @param errorMessage error message
     * @return view for page for exception
     */
    private fun process(model: Model, errorMessage: String): String {
        model.addAttribute("errorMessage", errorMessage)
        model.addAttribute("title", "Error")
        model.addAttribute("inner", false)

        return "errors"
    }

}
