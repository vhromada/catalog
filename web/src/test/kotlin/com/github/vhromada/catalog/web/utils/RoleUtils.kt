package com.github.vhromada.catalog.web.utils

import com.github.vhromada.catalog.web.connector.entity.ChangeRolesRequest
import com.github.vhromada.catalog.web.fo.RoleFO
import org.assertj.core.api.Assertions.assertThat

/**
 * A class represents utility class for roles.
 *
 * @author Vladimir Hromada
 */
object RoleUtils {

    /**
     * Returns FO for role.
     *
     * @return FO for role
     */
    fun getRole(): RoleFO {
        return RoleFO(roles = listOf("ROLE_ADMIN"))
    }

    /**
     * Asserts FO for role and request deep equals.
     *
     * @param expected expected FO for role
     * @param actual   actual request for changing roles
     */
    fun assertRequestDeepEquals(expected: RoleFO, actual: ChangeRolesRequest) {
        assertThat(actual.roles).isEqualTo(expected.roles)
    }

}
