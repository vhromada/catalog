package com.github.vhromada.catalog.web.service

/**
 * An interface represents encoder for password.
 *
 * @author Vladimir Hromada
 */
interface PasswordEncoder {

    /**
     * Encodes password.
     *
     * @param password password for encoding
     * @return encoded password
     */
    fun encode(password: String): String

}