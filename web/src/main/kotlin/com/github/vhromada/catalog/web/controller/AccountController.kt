package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.AccountConnector
import com.github.vhromada.catalog.web.fo.AccountFO
import com.github.vhromada.catalog.web.fo.AccountFilterFO
import com.github.vhromada.catalog.web.mapper.AccountMapper
import com.github.vhromada.catalog.web.service.AccountProvider
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Optional

/**
 * A class represents controller for accounts.
 *
 * @author Vladimir Hromada
 */
@Controller("accountController")
@RequestMapping("/accounts")
class AccountController(
    /**
     * Connector for accounts
     */
    private val connector: AccountConnector,
    /**
     * Mapper for accounts
     */
    private val mapper: AccountMapper,
    /**
     * Provider for account
     */
    private val accountProvider: AccountProvider
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of accounts.
     *
     * @param model    model
     * @param username username
     * @param page     index of page
     * @return view for page with list of accounts
     */
    @GetMapping
    fun showList(model: Model, @RequestParam("username") username: Optional<String>, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = AccountFilterFO(username = username.orElse(null)), page = page)
    }

    /**
     * Shows page with filtered list of accounts.
     *
     * @param model  model
     * @param filter FO for filter for accounts
     * @param page   index of page
     * @return view for page with filtered list of accounts
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: AccountFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page for editing account.
     *
     * @param model model
     * @return view for page for editing account
     */
    @GetMapping("/edit")
    fun showEdit(model: Model): String {
        return createFormView(model = model, account = AccountFO(username = null, password = null, copyPassword = null))
    }

    /**
     * Process editing account.
     *
     * @param model   model
     * @param account FO for account
     * @param errors  errors
     * @return view for redirect to page with list of accounts (no errors) or view for page for editing account (errors)
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("account") @Valid account: AccountFO, errors: Errors): String {
        if (errors.hasErrors()) {
            return createFormView(model = model, account = account)
        }
        var loggedAccount = accountProvider.getAccount()
        val stored = connector.updateCredentials(uuid = loggedAccount!!.uuid, credentials = mapper.map(source = account))
        loggedAccount = loggedAccount.copy(username = stored.username)
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(loggedAccount, null, loggedAccount.roles.map { SimpleGrantedAuthority(it) })

        return HOME_PAGE_REDIRECT_URL
    }

    /**
     * Cancel editing account.
     *
     * @return view for redirect to page with list of accounts
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return HOME_PAGE_REDIRECT_URL
    }

    /**
     * Shows page's view with list of accounts.
     *
     * @param model  model
     * @param filter FO for filter for accounts
     * @param page   index of page
     * @return page's view with list of accounts
     */
    private fun createListView(model: Model, filter: AccountFilterFO, page: Optional<Int>): String {
        val accountFilter = mapper.mapFilter(source = filter)
        accountFilter.page = page.orElse(1)
        accountFilter.limit = itemsPerPage
        val accounts = connector.search(filter = accountFilter)

        model.addAttribute("accounts", accounts.data)
        model.addAttribute("totalPages", accounts.pagesCount)
        model.addAttribute("currentPage", accounts.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Accounts")
        model.addAttribute("statistics", connector.getStatistics())

        return "account/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model   model
     * @param account FO for account
     * @return page's view with form
     */
    private fun createFormView(model: Model, account: AccountFO): String {
        model.addAttribute("account", account)
        model.addAttribute("title", "Edit account")

        return "account/form"
    }

    companion object {

        /**
         * Redirect URL to home page
         */
        private const val HOME_PAGE_REDIRECT_URL = "redirect:/"

    }

}
