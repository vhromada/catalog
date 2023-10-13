package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.GenreConnector
import com.github.vhromada.catalog.web.connector.PictureConnector
import com.github.vhromada.catalog.web.connector.ShowConnector
import com.github.vhromada.catalog.web.fo.MultipleNameFilterFO
import com.github.vhromada.catalog.web.fo.ShowFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import com.github.vhromada.catalog.web.mapper.ShowMapper
import jakarta.servlet.http.HttpServletRequest
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
 * A class represents controller for shows.
 *
 * @author Vladimir Hromada
 */
@Controller("showController")
@RequestMapping("/shows")
class ShowController(
    /**
     * Connector for shows
     */
    private val showConnector: ShowConnector,
    /**
     * Connector for pictures
     */
    private val pictureConnector: PictureConnector,
    /**
     * Connector for genres
     */
    private val genreConnector: GenreConnector,
    /**
     * Mapper for shows
     */
    private val showMapper: ShowMapper,
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
     * Shows page with list of shows.
     *
     * @param model        model
     * @param czechName    czech name
     * @param originalName original name
     * @param page         index of page
     * @return view for page with list of shows
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
     * Shows page with filtered list of shows.
     *
     * @param model  model
     * @param filter FO for filter for multiple names
     * @param page   index of page
     * @return view for page with filtered list of shows
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: MultipleNameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with detail of show.
     *
     * @param model model
     * @param uuid  UUID of showing show
     * @return view for page with detail of show
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("uuid") uuid: String): String {
        val show = showConnector.get(uuid = uuid)

        model.addAttribute("show", show)
        model.addAttribute("title", "Show detail")

        return "show/detail"
    }

    /**
     * Shows page for adding show.
     *
     * @param model model
     * @return view for page for adding show
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val show = ShowFO(
            uuid = null,
            czechName = null,
            originalName = null,
            csfd = null,
            imdb = false,
            wikiEn = null,
            imdbCode = null,
            wikiCz = null,
            picture = null,
            note = null,
            genres = null
        )
        return createAddFormView(model = model, show = show)
    }

    /**
     * Process adding show.
     *
     * @param model   model
     * @param show    FO for show
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of shows (no errors) or view for page for adding show (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping("/add")
    @Suppress("DuplicatedCode")
    fun processAdd(model: Model, @ModelAttribute("show") @Valid show: ShowFO, errors: Errors, request: HttpServletRequest): String {
        require(show.uuid == null) { "UUID must be null." }

        if (request.getParameter("create") != null) {
            if (errors.hasErrors()) {
                return createAddFormView(model = model, show = show)
            }
            showConnector.add(request = showMapper.mapRequest(source = show))
        }

        if (request.getParameter("choosePicture") != null) {
            return createAddFormView(model = model, show = show)
        }

        if (request.getParameter("removePicture") != null) {
            return createAddFormView(model = model, show = show.copy(picture = null))
        }

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing show.
     *
     * @param model model
     * @param uuid  UUID of editing show
     * @return view for page for editing show
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val show = showConnector.get(uuid = uuid)

        return createEditFormView(model = model, show = showMapper.map(source = show))
    }

    /**
     * Process editing show.
     *
     * @param model   model
     * @param show    FO for show
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of shows (no errors) or view for page for editing show (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping("/edit")
    @Suppress("DuplicatedCode")
    fun processEdit(model: Model, @ModelAttribute("show") @Valid show: ShowFO, errors: Errors, request: HttpServletRequest): String {
        require(show.uuid != null) { "UUID mustn't be null." }

        if (request.getParameter("update") != null) {
            if (errors.hasErrors()) {
                return createEditFormView(model = model, show = show)
            }
            showConnector.update(uuid = show.uuid, request = showMapper.mapRequest(source = show))
        }

        if (request.getParameter("choosePicture") != null) {
            return createEditFormView(model = model, show = show)
        }

        if (request.getParameter("removePicture") != null) {
            return createEditFormView(model = model, show = show.copy(picture = null))
        }

        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating show.
     *
     * @param uuid UUID of duplicating show
     * @return view for redirect to page with list of shows
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        showConnector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing show.
     *
     * @param uuid UUID of removing show
     * @return view for redirect to page with list of shows
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        showConnector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page's view with list of shows.
     *
     * @param model  model
     * @param filter FO for filter for multiple names
     * @param page   index of page
     * @return page's view with list of shows
     */
    private fun createListView(model: Model, filter: MultipleNameFilterFO, page: Optional<Int>): String {
        val showFilter = filterMapper.mapMultipleNameFilter(source = filter)
        showFilter.page = page.orElse(1)
        showFilter.limit = itemsPerPage
        val shows = showConnector.search(filter = showFilter)

        model.addAttribute("shows", shows.data)
        model.addAttribute("totalPages", shows.pagesCount)
        model.addAttribute("currentPage", shows.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Shows")
        model.addAttribute("statistics", showConnector.getStatistics())

        return "show/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param show   FO for show
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, show: ShowFO, title: String, action: String): String {
        val pictures = pictureConnector.getAll()
        val genres = genreConnector.getAll()

        model.addAttribute("show", show)
        model.addAttribute("title", title)
        model.addAttribute("pictures", pictures)
        model.addAttribute("genres", genres)
        model.addAttribute("action", action)

        return "show/form"
    }

    /**
     * Returns page's view with form for adding show.
     *
     * @param model model
     * @param show  FO for show
     * @return page's view with form for adding show
     */
    private fun createAddFormView(model: Model, show: ShowFO): String {
        return createFormView(model = model, show = show, title = "Add show", action = "add")
    }

    /**
     * Returns page's view with form for editing show.
     *
     * @param model model
     * @param show  FO for show
     * @return page's view with form for editing show
     */
    private fun createEditFormView(model: Model, show: ShowFO): String {
        return createFormView(model = model, show = show, title = "Edit show", action = "edit")
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/shows"

    }

}
