package com.github.vhromada.catalog.common.auth

import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest

/**
 * A class represents factory for creating auth context.
 *
 * @author Vladimir Hromada
 */
object AuthContextFactory {

    /**
     * Inits auth context.
     *
     * @param request request
     * @return auth context
     */
    fun init(request: ServletRequest): AuthContext {
        val auth = AuthContext()
        val user = getHeader(request = request, name = Header.USER)
        if (!user.isNullOrBlank()) {
            auth.addHeader(name = Header.USER, value = user)
        }
        return auth
    }

    /**
     * Returns header's value.
     *
     * @param request request
     * @param name header's name
     * @return header's value
     */
    @Suppress("SameParameterValue")
    private fun getHeader(request: ServletRequest, name: Header): String? {
        return if (request is HttpServletRequest) {
            request.getHeader(name.value)
        } else {
            null
        }
    }

}
