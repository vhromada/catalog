package com.github.vhromada.catalog.web.connector.common

import com.github.vhromada.catalog.common.auth.Header
import com.github.vhromada.catalog.web.security.Account
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * A class represents interceptor for processing header X-User.
 *
 * @author Vladimir Hromada
 */
@Component("userLoggingInterceptor")
class UserInterceptor : ClientHttpRequestInterceptor {

    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val auth = SecurityContextHolder.getContext().authentication
        if (auth is UsernamePasswordAuthenticationToken) {
            val principal = auth.principal
            if (principal is Account) {
                request.headers.set(Header.USER.value, principal.uuid)
            }
        }
        return execution.execute(request, body)
    }

}
