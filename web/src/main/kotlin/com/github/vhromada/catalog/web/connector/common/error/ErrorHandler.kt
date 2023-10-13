package com.github.vhromada.catalog.web.connector.common.error

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.vhromada.catalog.common.entity.Issue
import com.github.vhromada.catalog.common.entity.IssueList
import com.github.vhromada.catalog.common.result.Event
import com.github.vhromada.catalog.common.result.Result
import com.github.vhromada.catalog.common.result.Severity
import com.github.vhromada.catalog.web.connector.common.RestRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import java.util.Optional

/**
 * A class represents error handler.
 *
 * @author Vladimir Hromada
 */
@Component("errorHandler")
class ErrorHandler(
    /**
     * Mapper
     */
    private val mapper: ObjectMapper
) : ResponseErrorHandler {

    override fun handle(system: String, data: RestRequest<*>, exception: HttpStatusCodeException): Result<Unit> {
        val response = exception.responseBodyAsString
        val type = exception.responseHeaders?.contentType
        if (MediaType.APPLICATION_JSON.includes(type) && response.isNotBlank()) {
            val issues = parseJson(system = system, response = response)
            if (issues.isPresent) {
                val result = Result<Unit>()
                issues.get().issues.forEach { result.addEvent(event = Event(severity = Severity.ERROR, key = it.code, message = it.message)) }
                return result
            }
        }
        return Result.error(key = system, message = getMessage(exception = exception))
    }

    /**
     * Parses JSON.
     *
     * @param system   system
     * @param response parsing JSON
     * @return issues from JSON
     */
    private fun parseJson(system: String, response: String): Optional<IssueList> {
        if (response.indexOf("\"errors\"") != -1) {
            val issues = mapper.readValue(response, IssueList::class.java)
            return Optional.of(addSystemInfo(system = system, issues = issues))
        }
        return Optional.empty()
    }

    /**
     * Returns error message.
     *
     * @param exception HTTP exception
     * @return error message
     */
    private fun getMessage(exception: HttpStatusCodeException): String {
        return when (exception.statusCode) {
            HttpStatus.BAD_REQUEST -> "INVALID_REQUEST"
            HttpStatus.NOT_FOUND, HttpStatus.FORBIDDEN -> "OBJECT_NOT_FOUND"
            HttpStatus.PRECONDITION_FAILED -> "UNPROCESSABLE"
            HttpStatus.UNAUTHORIZED, HttpStatus.INTERNAL_SERVER_ERROR -> "INTERNAL_ERROR"
            else -> "INTERNAL_ERROR"
        }
    }

    /**
     * Adds info about system to issues.
     *
     * @param system system
     * @param issues list of issues
     * @return updated list of issues
     */
    private fun addSystemInfo(system: String, issues: IssueList): IssueList {
        return issues.copy(issues = issues.issues.map { addSystemInfo(system = system, issue = it) })
    }

    /**
     * Adds info about system to issue.
     *
     * @param system system
     * @param issue  issue
     * @return updated issue
     */
    private fun addSystemInfo(system: String, issue: Issue): Issue {
        val message = if (issue.message.contains("[") && issue.message.endsWith("]")) {
            issue.message.substring(0, issue.message.length - 1) + ", system=$system]"
        } else {
            "${issue.message} [system=$system]"
        }
        return issue.copy(message = message)
    }

}
