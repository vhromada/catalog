package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.GenreConnector
import com.github.vhromada.catalog.web.fo.GenreFO
import com.github.vhromada.catalog.web.fo.NameFilterFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import com.github.vhromada.catalog.web.mapper.GenreMapper
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
 * A class represents controller for genres.
 *
 * @author Vladimir Hromada
 */
@Controller("genreController")
@RequestMapping("/genres")
class GenreController(
    /**
     * Connector for genres
     */
    private val connector: GenreConnector,
    /**
     * Mapper for genres
     */
    private val genreMapper: GenreMapper,
    /**
     * Mapper for filters
     */
    private val filterMapper: FilterMapper
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of genres.
     *
     * @param model model
     * @param name  name
     * @param page  index of page
     * @return view for page with list of genres
     */
    @GetMapping
    fun showList(model: Model, @RequestParam("name") name: Optional<String>, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = NameFilterFO(name = name.orElse(null)), page = page)
    }

    /**
     * Shows page with filtered list of genres.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return view for page with filtered list of genres
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: NameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page for adding genre.
     *
     * @param model model
     * @return view for page for adding genre
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        return createFormView(model = model, genre = GenreFO(uuid = null, name = null), title = "Add genre", action = "add")
    }

    /**
     * Process adding genre.
     *
     * @param model  model
     * @param genre  FO for genre
     * @param errors errors
     * @return view for redirect to page with list of genres (no errors) or view for page for adding genre (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("genre") @Valid genre: GenreFO, errors: Errors): String {
        require(genre.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, genre = genre, title = "Add genre", action = "add")
        }
        connector.add(request = genreMapper.mapRequest(source = genre))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding genre.
     *
     * @return view for redirect to page with list of genres
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing genre.
     *
     * @param model model
     * @param uuid  UUID of editing genre
     * @return view for page for editing genre
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val genre = connector.get(uuid = uuid)

        return createFormView(model = model, genre = genreMapper.map(source = genre), title = "Edit genre", action = "edit")
    }

    /**
     * Process editing genre.
     *
     * @param model  model
     * @param genre  FO for genre
     * @param errors errors
     * @return view for redirect to page with list of genres (no errors) or view for page for editing genre (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("genre") @Valid genre: GenreFO, errors: Errors): String {
        require(genre.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, genre = genre, title = "Edit genre", action = "edit")
        }
        connector.update(uuid = genre.uuid, request = genreMapper.mapRequest(source = genre))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing genre.
     *
     * @return view for redirect to page with list of genres
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating genre.
     *
     * @param uuid UUID of duplicating genre
     * @return view for redirect to page with list of genres
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        connector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing genre.
     *
     * @param uuid UUID of removing genre
     * @return view for redirect to page with list of genres
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        connector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }


    /**
     * Shows page's view with list of genres.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return page's view with list of genres
     */
    private fun createListView(model: Model, filter: NameFilterFO, page: Optional<Int>): String {
        val genreFilter = filterMapper.mapNameFilter(source = filter)
        genreFilter.page = page.orElse(1)
        genreFilter.limit = itemsPerPage
        val genres = connector.search(filter = genreFilter)

        model.addAttribute("genres", genres.data)
        model.addAttribute("totalPages", genres.pagesCount)
        model.addAttribute("currentPage", genres.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Genres")
        model.addAttribute("statistics", connector.getStatistics())

        return "genre/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param genre  FO for genre
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, genre: GenreFO, title: String, action: String): String {
        model.addAttribute("genre", genre)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "genre/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/genres"

    }

}
