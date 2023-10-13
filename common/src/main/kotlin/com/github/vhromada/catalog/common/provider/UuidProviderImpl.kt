package com.github.vhromada.catalog.common.provider

import org.springframework.stereotype.Component
import java.util.UUID

/**
 * A class represents implementation of provider for UUID.
 *
 * @author Vladimir Hromada
 */
@Component("uuidProvider")
class UuidProviderImpl : UuidProvider {

    override fun getUuid(): String {
        return UUID.randomUUID().toString()
    }

}
