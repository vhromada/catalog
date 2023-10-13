package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.AuthorConnector
import com.github.vhromada.catalog.web.fo.AuthorFO
import com.github.vhromada.catalog.web.fo.AuthorFilterFO
import com.github.vhromada.catalog.web.mapper.AuthorMapper
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.Optional

/**
 * A class represents controller for authors.
 *
 * @author Vladimir Hromada
 */
@Controller("authorController")
@RequestMapping("/authors")
class AuthorController(
    /**
     * Connector for authors
     */
    private val connector: AuthorConnector,
    /**
     * Mapper for authors
     */
    private val authorMapper: AuthorMapper
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of authors.
     *
     * @param model      model
     * @param firstName  first name
     * @param middleName middle name
     * @param lastName   last name
     * @param page       index of page
     * @return view for page with list of authors
     */
    @GetMapping
    fun showList(
        model: Model,
        @RequestParam("firstName") firstName: Optional<String>,
        @RequestParam("middleName") middleName: Optional<String>,
        @RequestParam("lastName") lastName: Optional<String>,
        @RequestParam("page") page: Optional<Int>
    ): String {
        val filter = AuthorFilterFO(
            firstName = firstName.orElse(null),
            middleName = middleName.orElse(null),
            lastName = lastName.orElse(null)
        )
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with filtered list of authors.
     *
     * @param model  model
     * @param filter FO for filter for authors
     * @param page   index of page
     * @return view for page with filtered list of authors
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: AuthorFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page for adding author.
     *
     * @param model model
     * @return view for page for adding author
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val author = AuthorFO(
            uuid = null,
            firstName = null,
            middleName = null,
            lastName = null
        )
        return createFormView(model = model, author = author, title = "Add author", action = "add")
    }

    /**
     * Process adding author.
     *
     * @param model  model
     * @param author FO for author
     * @param errors errors
     * @return view for redirect to page with list of authors (no errors) or view for page for adding author (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("author") @Valid author: AuthorFO, errors: Errors): String {
        require(author.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, author = author, title = "Add author", action = "add")
        }
        connector.add(request = authorMapper.mapRequest(source = author))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing author.
     *
     * @param model model
     * @param uuid  UUID of editing author
     * @return view for page for editing author
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val author = connector.get(uuid = uuid)

        return createFormView(model = model, author = authorMapper.map(source = author), title = "Edit author", action = "edit")
    }

    /**
     * Process editing author.
     *
     * @param model  model
     * @param author  FO for author
     * @param errors errors
     * @return view for redirect to page with list of authors (no errors) or view for page for editing author (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("author") @Valid author: AuthorFO, errors: Errors): String {
        require(author.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, author = author, title = "Edit author", action = "edit")
        }
        connector.update(uuid = author.uuid, request = authorMapper.mapRequest(source = author))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating author.
     *
     * @param uuid UUID of duplicating author
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        connector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing author.
     *
     * @param uuid UUID of removing author
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        connector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page's view with list of authors.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return page's view with list of authors
     */
    private fun createListView(model: Model, filter: AuthorFilterFO, page: Optional<Int>): String {
        val authorFilter = authorMapper.mapFilter(source = filter)
        authorFilter.page = page.orElse(1)
        authorFilter.limit = itemsPerPage
        val authors = connector.search(filter = authorFilter)

        model.addAttribute("authors", authors.data)
        model.addAttribute("totalPages", authors.pagesCount)
        model.addAttribute("currentPage", authors.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Authors")
        model.addAttribute("statistics", connector.getStatistics())

        return "author/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param author FO for author
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, author: AuthorFO, title: String, action: String): String {
        model.addAttribute("author", author)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "author/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/authors"

    }

}
