package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.common.filter.FieldOperation
import com.github.vhromada.catalog.web.connector.entity.Credentials
import com.github.vhromada.catalog.web.connector.filter.AccountFilter
import com.github.vhromada.catalog.web.fo.AccountFO
import com.github.vhromada.catalog.web.fo.AccountFilterFO
import com.github.vhromada.catalog.web.mapper.AccountMapper
import com.github.vhromada.catalog.web.service.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for accounts.
 *
 * @author Vladimir Hromada
 */
@Component("accountMapper")
class AccountMapperImpl(
    /**
     * Encoder for password
     */
    private val passwordEncoder: PasswordEncoder
) : AccountMapper {

    override fun map(source: AccountFO): Credentials {
        return Credentials(
            username = source.username!!,
            password = passwordEncoder.encode(password = source.password!!)
        )
    }

    override fun mapFilter(source: AccountFilterFO): AccountFilter {
        return AccountFilter(
            username = source.username,
            usernameOperation = FieldOperation.LIKE
        )
    }

}
