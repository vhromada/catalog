package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.AccountConnector
import com.github.vhromada.catalog.web.connector.RoleConnector
import com.github.vhromada.catalog.web.fo.RoleFO
import com.github.vhromada.catalog.web.mapper.RoleMapper
import com.github.vhromada.catalog.web.service.AccountProvider
import jakarta.validation.Valid
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * A class represents controller for roles.
 *
 * @author Vladimir Hromada
 */
@Controller("roleController")
@RequestMapping("/accounts/{accountUuid}/roles")
class RoleController(
    /**
     * Connector for roles
     */
    private val connector: RoleConnector,
    /**
     * Connector for accounts
     */
    private val accountConnector: AccountConnector,
    /**
     * Mapper for roles
     */
    private val mapper: RoleMapper,
    /**
     * Provider for account
     */
    private val accountProvider: AccountProvider
) {

    /**
     * Shows page for editing roles.
     *
     * @param model       model
     * @param accountUuid account UUID
     * @return view for page for editing account
     */
    @GetMapping("/edit")
    fun showEdit(model: Model, @PathVariable("accountUuid") accountUuid: String): String {
        val account = accountConnector.get(uuid = accountUuid)

        return createFormView(model = model, role = RoleFO(roles = account.roles), accountUuid = accountUuid)
    }

    /**
     * Process editing roles.
     *
     * @param model       model
     * @param accountUuid account UUID
     * @param role        FO for role
     * @param errors      errors
     * @return view for redirect to page with list of accounts (no errors) or view for page for editing account (errors)
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @PathVariable("accountUuid") accountUuid: String, @ModelAttribute("role") @Valid role: RoleFO, errors: Errors): String {
        if (errors.hasErrors()) {
            return createFormView(model = model, role = role, accountUuid = accountUuid)
        }
        var loggedAccount = accountProvider.getAccount()
        val stored = accountConnector.updateRoles(uuid = accountUuid, request = mapper.map(source = role))
        if (loggedAccount!!.uuid == accountUuid) {
            loggedAccount = loggedAccount.copy(roles = stored.roles)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(loggedAccount, null, loggedAccount.roles.map { SimpleGrantedAuthority(it) })
        }

        return ACCOUNTS_REDIRECT_URL
    }

    /**
     * Cancel editing roles.
     *
     * @param accountUuid account UUID
     * @return view for redirect to page with list of accounts
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(@PathVariable("accountUuid") accountUuid: String): String {
        accountConnector.get(uuid = accountUuid)

        return ACCOUNTS_REDIRECT_URL
    }

    /**
     * Returns page's view with form.
     *
     * @param model       model
     * @param role        FO for role
     * @param accountUuid account UUID
     * @return page's view with form
     */
    private fun createFormView(model: Model, role: RoleFO, accountUuid: String): String {
        val roles = connector.getRoles()

        model.addAttribute("role", role)
        model.addAttribute("account", accountUuid)
        model.addAttribute("roles", roles)
        model.addAttribute("title", "Edit roles")

        return "roles/form"
    }

    companion object {

        /**
         * Redirect URL to accounts
         */
        private const val ACCOUNTS_REDIRECT_URL = "redirect:/accounts"

    }

}
