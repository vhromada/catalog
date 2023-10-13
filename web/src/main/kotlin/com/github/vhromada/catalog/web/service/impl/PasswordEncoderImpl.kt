package com.github.vhromada.catalog.web.service.impl

import com.github.vhromada.catalog.web.service.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

/**
 * A class represents implementation of provider for password.
 *
 * @author Vladimir Hromada
 */
@Component
class PasswordEncoderImpl : PasswordEncoder {

    override fun encode(password: String): String {
        return BCrypt.hashpw(password, "\$2a\$10\$GbU31T9G2sgIpLZeMOU2Mu")
    }

}
