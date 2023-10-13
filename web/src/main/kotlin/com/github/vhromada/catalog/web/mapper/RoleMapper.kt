package com.github.vhromada.catalog.web.mapper

import com.github.vhromada.catalog.web.connector.entity.ChangeRolesRequest
import com.github.vhromada.catalog.web.fo.RoleFO

/**
 * An interface represents mapper for roles.
 *
 * @author Vladimir Hromada
 */
interface RoleMapper {

    /**
     * Maps FO for roles.
     *
     * @param source FO for roles
     * @return mapped request for changing roles
     */
    fun map(source: RoleFO): ChangeRolesRequest

}
