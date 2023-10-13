package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.AuthorConnector
import com.github.vhromada.catalog.web.connector.BookConnector
import com.github.vhromada.catalog.web.fo.BookFO
import com.github.vhromada.catalog.web.fo.MultipleNameFilterFO
import com.github.vhromada.catalog.web.mapper.BookMapper
import com.github.vhromada.catalog.web.mapper.FilterMapper
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
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@Controller("bookController")
@RequestMapping("/books")
class BookController(
    /**
     * Connector for books
     */
    private val bookConnector: BookConnector,
    /**
     * Connector for authors
     */
    private val authorConnector: AuthorConnector,
    /**
     * Mapper for books
     */
    private val bookMapper: BookMapper,
    /**
     * Mapper for filters
     */
    private val filterMapper: FilterMapper
) {

    /**
     * Count of items bookn on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Books page with list of books.
     *
     * @param model        model
     * @param czechName    czech name
     * @param originalName original name
     * @param page         index of page
     * @return view for page with list of books
     */
    @GetMapping
    fun showList(
        model: Model,
        @RequestParam("czechName") czechName: Optional<String>,
        @RequestParam("originalName") originalName: Optional<String>,
        @RequestParam("page") page: Optional<Int>
    ): String {
        val filter = MultipleNameFilterFO(
            czechName = czechName.orElse(null),
            originalName = originalName.orElse(null)
        )
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with filtered list of books.
     *
     * @param model  model
     * @param filter FO for filter for multiple names
     * @param page   index of page
     * @return view for page with filtered list of books
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: MultipleNameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with detail of book.
     *
     * @param model model
     * @param uuid  UUID of showing book
     * @return view for page with detail of book
     */
    @GetMapping("/{uuid}/detail")
    fun bookDetail(model: Model, @PathVariable("uuid") uuid: String): String {
        val book = bookConnector.get(uuid = uuid)

        model.addAttribute("book", book)
        model.addAttribute("title", "Book detail")

        return "book/detail"
    }

    /**
     * Shows page for adding book.
     *
     * @param model model
     * @return view for page for adding book
     */
    @GetMapping("/add")
    fun bookAdd(model: Model): String {
        val book = BookFO(
            uuid = null,
            czechName = null,
            originalName = null,
            description = null,
            note = null,
            authors = null
        )
        return createFormView(model = model, book = book, title = "Add book", action = "add")
    }

    /**
     * Process adding book.
     *
     * @param model   model
     * @param book    FO for book
     * @param errors  errors
     * @return view for redirect to page with list of books (no errors) or view for page for adding book (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("book") @Valid book: BookFO, errors: Errors): String {
        require(book.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, book = book, title = "Add book", action = "add")
        }
        bookConnector.add(request = bookMapper.mapRequest(source = book))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing book.
     *
     * @param model model
     * @param uuid  UUID of editing book
     * @return view for page for editing book
     */
    @GetMapping("/edit/{uuid}")
    fun bookEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val book = bookConnector.get(uuid = uuid)

        return createFormView(model = model, book = bookMapper.map(source = book), title = "Edit book", action = "edit")
    }

    /**
     * Process editing book.
     *
     * @param model  model
     * @param book   FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for editing book (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("book") @Valid book: BookFO, errors: Errors): String {
        require(book.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, book = book, title = "Edit book", action = "edit")
        }
        bookConnector.update(uuid = book.uuid, request = bookMapper.mapRequest(source = book))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating book.
     *
     * @param uuid UUID of duplicating book
     * @return view for redirect to page with list of books
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        bookConnector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing book.
     *
     * @param uuid UUID of removing book
     * @return view for redirect to page with list of books
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        bookConnector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page's view with list of books.
     *
     * @param model  model
     * @param filter FO for filter for multiple names
     * @param page   index of page
     * @return page's view with list of books
     */
    private fun createListView(model: Model, filter: MultipleNameFilterFO, page: Optional<Int>): String {
        val bookFilter = filterMapper.mapMultipleNameFilter(source = filter)
        bookFilter.page = page.orElse(1)
        bookFilter.limit = itemsPerPage
        val books = bookConnector.search(filter = bookFilter)

        model.addAttribute("books", books.data)
        model.addAttribute("totalPages", books.pagesCount)
        model.addAttribute("currentPage", books.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Books")
        model.addAttribute("statistics", bookConnector.getStatistics())

        return "book/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param book   FO for book
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, book: BookFO, title: String, action: String): String {
        val authors = authorConnector.getAll()

        model.addAttribute("book", book)
        model.addAttribute("title", title)
        model.addAttribute("authors", authors)
        model.addAttribute("action", action)

        return "book/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/books"

    }

}
