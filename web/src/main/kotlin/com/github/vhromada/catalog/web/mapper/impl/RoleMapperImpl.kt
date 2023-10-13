package com.github.vhromada.catalog.web.mapper.impl

import com.github.vhromada.catalog.web.connector.entity.ChangeRolesRequest
import com.github.vhromada.catalog.web.fo.RoleFO
import com.github.vhromada.catalog.web.mapper.RoleMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for roles.
 *
 * @author Vladimir Hromada
 */
@Component("roleMapper")
class RoleMapperImpl : RoleMapper {

    override fun map(source: RoleFO): ChangeRolesRequest {
        return ChangeRolesRequest(roles = source.roles!!)
    }

}
