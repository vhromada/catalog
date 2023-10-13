package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.GenreConnector
import com.github.vhromada.catalog.web.connector.MovieConnector
import com.github.vhromada.catalog.web.connector.PictureConnector
import com.github.vhromada.catalog.web.connector.RegisterConnector
import com.github.vhromada.catalog.web.fo.MovieFO
import com.github.vhromada.catalog.web.fo.MultipleNameFilterFO
import com.github.vhromada.catalog.web.fo.TimeFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import com.github.vhromada.catalog.web.mapper.MovieMapper
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
 * A class represents controller for movies.
 *
 * @author Vladimir Hromada
 */
@Controller("movieController")
@RequestMapping("/movies")
class MovieController(
    /**
     * Connector for movies
     */
    private val movieConnector: MovieConnector,
    /**
     * Connector for pictures
     */
    private val pictureConnector: PictureConnector,
    /**
     * Connector for genres
     */
    private val genreConnector: GenreConnector,
    /**
     * Connector for registers
     */
    private val registerConnector: RegisterConnector,
    /**
     * Mapper for movies
     */
    private val movieMapper: MovieMapper,
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
     * Shows page with list of movies.
     *
     * @param model        model
     * @param czechName    czech name
     * @param originalName original name
     * @param page         index of page
     * @return view for page with list of movies
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
     * Shows page with filtered list of movies.
     *
     * @param model  model
     * @param filter FO for filter for multiple names
     * @param page   index of page
     * @return view for page with filtered list of movies
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: MultipleNameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with detail of movie.
     *
     * @param model model
     * @param uuid  UUID of showing movie
     * @return view for page with detail of movie
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("uuid") uuid: String): String {
        val result = movieConnector.get(uuid = uuid)

        model.addAttribute("movie", result)
        model.addAttribute("title", "Movie detail")

        return "movie/detail"
    }

    /**
     * Shows page for adding movie.
     *
     * @param model model
     * @return view for page for adding movie
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val movie = MovieFO(
            uuid = null,
            czechName = null,
            originalName = null,
            year = null,
            languages = null,
            subtitles = null,
            media = null,
            csfd = null,
            imdb = false,
            imdbCode = null,
            wikiEn = null,
            wikiCz = null,
            picture = null,
            note = null,
            genres = null
        )
        return createAddFormView(model = model, movie = movie)
    }

    /**
     * Process adding movie.
     *
     * @param model   model
     * @param movie   FO for movie
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for adding movie (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping("/add")
    @Suppress("DuplicatedCode")
    fun processAdd(model: Model, @ModelAttribute("movie") @Valid movie: MovieFO, errors: Errors, request: HttpServletRequest): String {
        require(movie.uuid == null) { "UUID must be null." }

        if (request.getParameter("create") != null) {
            if (errors.hasErrors()) {
                return createAddFormView(model = model, movie = movie)
            }
            movieConnector.add(request = movieMapper.mapRequest(source = movie))
        }

        if (request.getParameter("addMedium") != null) {
            val media = if (movie.media == null) mutableListOf() else movie.media!!.toMutableList()
            media.add(TimeFO(hours = null, minutes = null, seconds = null))

            return createAddFormView(model = model, movie = movie.copy(media = media))
        }

        return processAddMovie(model = model, movie = movie, request = request)
    }

    /**
     * Shows page for editing movie.
     *
     * @param model model
     * @param uuid  UUID of editing movie
     * @return view for page for editing movie
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val movie = movieConnector.get(uuid = uuid)

        return createEditFormView(model = model, movie = movieMapper.map(source = movie))
    }

    /**
     * Process editing movie.
     *
     * @param model   model
     * @param movie   FO for movie
     * @param errors  errors
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for editing movie (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping("/edit")
    @Suppress("DuplicatedCode")
    fun processEdit(model: Model, @ModelAttribute("movie") @Valid movie: MovieFO, errors: Errors, request: HttpServletRequest): String {
        require(movie.uuid != null) { "UUID mustn't be null." }

        if (request.getParameter("update") != null) {
            if (errors.hasErrors()) {
                return createEditFormView(model = model, movie = movie)
            }

            movieConnector.update(uuid = movie.uuid, request = movieMapper.mapRequest(source = movie))
        }

        if (request.getParameter("addMedium") != null) {
            val media = movie.media!!.toMutableList()
            media.add(TimeFO(hours = null, minutes = null, seconds = null))

            return createEditFormView(model = model, movie = movie.copy(media = media))
        }

        return processEditMovie(model = model, movie = movie, request = request)
    }

    /**
     * Process duplicating movie.
     *
     * @param uuid UUID of duplicating movie
     * @return view for redirect to page with list of movies
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        movieConnector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing movie.
     *
     * @param uuid UUID of removing movie
     * @return view for redirect to page with list of movies
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        movieConnector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process adding movie.
     *
     * @param model   model
     * @param movie   FO for movie
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for adding movie (errors)
     */
    private fun processAddMovie(model: Model, movie: MovieFO, request: HttpServletRequest): String {
        if (request.getParameter("choosePicture") != null) {
            return createAddFormView(model = model, movie = movie)
        }

        if (request.getParameter("removePicture") != null) {
            return createAddFormView(model = model, movie = movie.copy(picture = null))
        }

        val index = getRemoveIndex(request = request)
        if (index != null) {
            val media = movie.media!!.toMutableList()
            media.removeAt(index)

            return createAddFormView(model = model, movie = movie.copy(media = media))
        }

        return LIST_REDIRECT_URL
    }

    /**
     * Process editing movie.
     *
     * @param model   model
     * @param movie   FO for movie
     * @param request HTTP request
     * @return view for redirect to page with list of movies (no errors) or view for page for editing movie (errors)
     */
    private fun processEditMovie(model: Model, movie: MovieFO, request: HttpServletRequest): String {
        if (request.getParameter("choosePicture") != null) {
            return createEditFormView(model = model, movie = movie)
        }

        if (request.getParameter("removePicture") != null) {
            return createEditFormView(model = model, movie = movie.copy(picture = null))
        }

        val index = getRemoveIndex(request = request)
        if (index != null) {
            val media = movie.media!!.toMutableList()
            media.removeAt(index)

            return createEditFormView(model = model, movie = movie.copy(media = media))
        }

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page's view with list of movies.
     *
     * @param model  model
     * @param filter FO for filter for multiple names
     * @param page   index of page
     * @return page's view with list of movies
     */
    private fun createListView(model: Model, filter: MultipleNameFilterFO, page: Optional<Int>): String {
        val movieFilter = filterMapper.mapMultipleNameFilter(source = filter)
        movieFilter.page = page.orElse(1)
        movieFilter.limit = itemsPerPage
        val movies = movieConnector.search(filter = movieFilter)

        model.addAttribute("movies", movies.data)
        model.addAttribute("totalPages", movies.pagesCount)
        model.addAttribute("currentPage", movies.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Movies")
        model.addAttribute("statistics", movieConnector.getStatistics())

        return "movie/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param movie  FO for movie
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, movie: MovieFO, title: String, action: String): String {
        val languages = registerConnector.getLanguages()
        val subtitles = registerConnector.getSubtitles()
        val pictures = pictureConnector.getAll()
        val genres = genreConnector.getAll()

        model.addAttribute("movie", movie)
        model.addAttribute("title", title)
        model.addAttribute("languages", languages)
        model.addAttribute("subtitles", subtitles)
        model.addAttribute("pictures", pictures)
        model.addAttribute("genres", genres)
        model.addAttribute("action", action)

        return "movie/form"
    }

    /**
     * Returns page's view with form for adding movie.
     *
     * @param model model
     * @param movie FO for movie
     * @return page's view with form for adding movie
     */
    private fun createAddFormView(model: Model, movie: MovieFO): String {
        return createFormView(model = model, movie = movie, title = "Add movie", action = "add")
    }

    /**
     * Returns page's view with form for editing movie.
     *
     * @param model model
     * @param movie FO for movie
     * @return page's view with form for editing movie
     */
    private fun createEditFormView(model: Model, movie: MovieFO): String {
        return createFormView(model = model, movie = movie, title = "Edit movie", action = "edit")
    }

    /**
     * Returns index of removing media.
     *
     * @param request HTTP request
     * @return index of removing media
     */
    private fun getRemoveIndex(request: HttpServletRequest): Int? {
        var index: Int? = null
        val names = request.parameterNames
        while (names.hasMoreElements() && index == null) {
            val name = names.nextElement()
            if (name.startsWith("removeMedium")) {
                index = (name.substring(12).toInt())
            }
        }

        return index
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/movies"

    }

}
