package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.BookConnector
import com.github.vhromada.catalog.web.connector.BookItemConnector
import com.github.vhromada.catalog.web.connector.RegisterConnector
import com.github.vhromada.catalog.web.fo.BookItemFO
import com.github.vhromada.catalog.web.mapper.BookItemMapper
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
 * A class represents controller for book items.
 *
 * @author Vladimir Hromada
 */
@Controller("bookItemController")
@RequestMapping("/books/{bookUuid}/items")
class BookItemController(
    /**
     * Connector for book items
     */
    private val bookItemConnector: BookItemConnector,
    /**
     * Connector for books
     */
    private val bookConnector: BookConnector,
    /**
     * Connector for registers
     */
    private val registerConnector: RegisterConnector,
    /**
     * Mapper for book items
     */
    private val mapper: BookItemMapper
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of book items.
     *
     * @param model    model
     * @param bookUuid book UUID
     * @param page     index of page
     * @return view for page with list of book items
     */
    @GetMapping
    fun bookList(model: Model, @PathVariable("bookUuid") bookUuid: String, @RequestParam("page") page: Optional<Int>): String {
        val bookItemFilter = PagingFilter()
        bookItemFilter.page = page.orElse(1)
        bookItemFilter.limit = itemsPerPage
        val bookItems = bookItemConnector.search(book = bookUuid, filter = bookItemFilter)

        model.addAttribute("bookItems", bookItems.data)
        model.addAttribute("totalPages", bookItems.pagesCount)
        model.addAttribute("currentPage", bookItems.pageNumber)
        model.addAttribute("book", bookUuid)
        model.addAttribute("title", "Book items")

        return "bookItem/index"
    }

    /**
     * Shows page with detail of book item.
     *
     * @param model    model
     * @param bookUuid book UUID
     * @param uuid     UUID of showing book item
     * @return view for page with detail of book item
     */
    @GetMapping("/{uuid}/detail")
    fun bookDetail(model: Model, @PathVariable("bookUuid") bookUuid: String, @PathVariable("uuid") uuid: String): String {
        val bookItem = bookItemConnector.get(book = bookUuid, uuid = uuid)

        model.addAttribute("bookItem", bookItem)
        model.addAttribute("book", bookUuid)
        model.addAttribute("title", "Book item detail")

        return "bookItem/detail"
    }

    /**
     * Shows page for adding book item.
     *
     * @param model    model
     * @param bookUuid book UUID
     * @return view for page for adding book item
     */
    @GetMapping("/add")
    fun bookAdd(model: Model, @PathVariable("bookUuid") bookUuid: String): String {
        bookConnector.get(uuid = bookUuid)

        val bookItem = BookItemFO(
            uuid = null,
            languages = null,
            format = null,
            note = null
        )
        return createFormView(model = model, bookItem = bookItem, bookUuid = bookUuid, title = "Add book item", action = "add")
    }

    /**
     * Process adding book item.
     *
     * @param model    model
     * @param bookUuid book UUID
     * @param bookItem FO for book item
     * @param errors   errors
     * @return view for redirect to page with list of book items (no errors) or view for page for adding book item (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @PathVariable("bookUuid") bookUuid: String, @ModelAttribute("bookItem") @Valid bookItem: BookItemFO, errors: Errors): String {
        require(bookItem.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, bookItem = bookItem, bookUuid = bookUuid, title = "Add book item", action = "add")
        }
        bookItemConnector.add(book = bookUuid, request = mapper.mapRequest(source = bookItem))

        return getListRedirectUrl(bookUuid = bookUuid)
    }

    /**
     * Cancel adding book item.
     *
     * @param bookUuid book UUID
     * @return view for redirect to page with list of book items
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(@PathVariable("bookUuid") bookUuid: String): String {
        return getListRedirectUrl(bookUuid = bookUuid)
    }

    /**
     * Shows page for editing book item.
     *
     * @param model    model
     * @param bookUuid book UUID
     * @param uuid     UUID of editing book item
     * @return view for page for editing book item
     */
    @GetMapping("/edit/{uuid}")
    fun bookEdit(model: Model, @PathVariable("bookUuid") bookUuid: String, @PathVariable("uuid") uuid: String): String {
        val bookItem = bookItemConnector.get(book = bookUuid, uuid = uuid)

        return createFormView(model = model, bookItem = mapper.map(source = bookItem), bookUuid = bookUuid, title = "Edit book item", action = "edit")
    }

    /**
     * Process editing book item.
     *
     * @param model    model
     * @param bookUuid book UUID
     * @param bookItem FO for book item
     * @param errors   errors
     * @return view for redirect to page with list of book items (no errors) or view for page for editing book item (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @PathVariable("bookUuid") bookUuid: String, @ModelAttribute("bookItem") @Valid bookItem: BookItemFO, errors: Errors): String {
        require(bookItem.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, bookItem = bookItem, bookUuid = bookUuid, title = "Edit book item", action = "edit")
        }
        bookItemConnector.update(book = bookUuid, uuid = bookItem.uuid, request = mapper.mapRequest(source = bookItem))

        return getListRedirectUrl(bookUuid = bookUuid)
    }

    /**
     * Cancel editing book item.
     *
     * @param bookUuid book UUID
     * @return view for redirect to page with list of book items
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(@PathVariable("bookUuid") bookUuid: String): String {
        return getListRedirectUrl(bookUuid = bookUuid)
    }

    /**
     * Process duplicating book item.
     *
     * @param bookUuid book UUID
     * @param uuid     UUID of duplicating book item
     * @return view for redirect to page with list of book items
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("bookUuid") bookUuid: String, @PathVariable("uuid") uuid: String): String {
        bookItemConnector.duplicate(book = bookUuid, uuid = uuid)

        return getListRedirectUrl(bookUuid = bookUuid)
    }

    /**
     * Process removing book item.
     *
     * @param bookUuid book UUID
     * @param uuid     UUID of removing book item
     * @return view for redirect to page with list of book items
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("bookUuid") bookUuid: String, @PathVariable("uuid") uuid: String): String {
        bookItemConnector.remove(book = bookUuid, uuid = uuid)

        return getListRedirectUrl(bookUuid = bookUuid)
    }

    /**
     * Returns page's view with form.
     *
     * @param model    model
     * @param bookItem FO for book item
     * @param bookUuid book UUID
     * @param title    page's title
     * @param action   action
     * @return page's view with form
     */
    private fun createFormView(model: Model, bookItem: BookItemFO, bookUuid: String, title: String, action: String): String {
        val languages = registerConnector.getLanguages()
        val formats = registerConnector.getBookItemFormats()

        model.addAttribute("bookItem", bookItem)
        model.addAttribute("book", bookUuid)
        model.addAttribute("languages", languages)
        model.addAttribute("formats", formats)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "bookItem/form"
    }

    /**
     * Returns redirect URL to list.
     *
     * @param bookUuid book UUID
     * @return redirect URL to list
     */
    private fun getListRedirectUrl(bookUuid: String): String {
        return "redirect:/books/$bookUuid/items"
    }

}
