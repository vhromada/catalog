package com.github.vhromada.catalog.common.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.vhromada.catalog.common.exception.InputException
import com.github.vhromada.catalog.common.mapper.IssueMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.GenericFilterBean
import java.nio.charset.StandardCharsets

/**
 * A class represents filter for auth context.
 *
 * @author Vladimir Hromada
 */
class AuthContextFilter(
    /**
     * Object mapper
     */
    private val objectMapper: ObjectMapper,
    /**
     * Mapper between result and issues
     */
    private val issueMapper: IssueMapper
) : GenericFilterBean() {

    /**
     * Logger
     */
    private val log = KotlinLogging.logger {}

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            AuthContextHolder.init(authContext = AuthContextFactory.init(request = request))
            chain.doFilter(request, response)
        } catch (exception: InputException) {
            log.info(exception) { exception.javaClass.simpleName }
            if (response is HttpServletResponse) {
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                response.characterEncoding = StandardCharsets.UTF_8.name()
                response.status = exception.httpStatus.value()
                response.getWriter().write(objectMapper.writeValueAsString(issueMapper.map(source = exception.result)))
            } else {
                throw exception
            }
        } catch (exception: Exception) {
            log.error(exception) { exception.javaClass.simpleName }
            throw exception
        } finally {
            AuthContextHolder.clear()
        }
    }

}
