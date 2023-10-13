package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.GameConnector
import com.github.vhromada.catalog.web.connector.RegisterConnector
import com.github.vhromada.catalog.web.fo.GameFO
import com.github.vhromada.catalog.web.fo.NameFilterFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import com.github.vhromada.catalog.web.mapper.GameMapper
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
 * A class represents controller for games.
 *
 * @author Vladimir Hromada
 */
@Controller("gameController")
@RequestMapping("/games")
class GameController(
    /**
     * Connector for games
     */
    private val gameConnector: GameConnector,
    /**
     * Connector for registers
     */
    private val registerConnector: RegisterConnector,
    /**
     * Mapper for games
     */
    private val gameMapper: GameMapper,
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
     * Shows page with list of games.
     *
     * @param model model
     * @param name  name
     * @param page  index of page
     * @return view for page with list of games
     */
    @GetMapping
    fun showList(model: Model, @RequestParam("name") name: Optional<String>, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = NameFilterFO(name = name.orElse(null)), page = page)
    }

    /**
     * Shows page with filtered list of games.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return view for page with filtered list of games
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: NameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with detail of game.
     *
     * @param model model
     * @param uuid  UUID of showing game
     * @return view for page with detail of game
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("uuid") uuid: String): String {
        val game = gameConnector.get(uuid = uuid)

        model.addAttribute("game", game)
        model.addAttribute("title", "Game detail")

        return "game/detail"
    }

    /**
     * Shows page for adding game.
     *
     * @param model model
     * @return view for page for adding game
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val game = GameFO(
            uuid = null,
            name = null,
            wikiEn = null,
            wikiCz = null,
            mediaCount = null,
            format = null,
            crack = null,
            serialKey = null,
            patch = null,
            trainer = null,
            trainerData = null,
            editor = null,
            saves = null,
            otherData = null,
            note = null
        )
        return createFormView(model = model, game = game, title = "Add game", action = "add")
    }

    /**
     * Process adding game.
     *
     * @param model  model
     * @param game   FO for game
     * @param errors errors
     * @return view for redirect to page with list of games (no errors) or view for page for adding game (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("game") @Valid game: GameFO, errors: Errors): String {
        require(game.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, game = game, title = "Add game", action = "add")
        }
        gameConnector.add(request = gameMapper.mapRequest(source = game))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding game.
     *
     * @return view for redirect to page with list of games
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing game.
     *
     * @param model model
     * @param uuid  UUID of editing game
     * @return view for page for editing game
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val game = gameConnector.get(uuid = uuid)

        return createFormView(model = model, game = gameMapper.map(source = game), title = "Edit game", action = "edit")
    }

    /**
     * Process editing game.
     *
     * @param model  model
     * @param game   FO for game
     * @param errors errors
     * @return view for redirect to page with list of games (no errors) or view for page for editing game (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("game") @Valid game: GameFO, errors: Errors): String {
        require(game.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, game = game, title = "Edit game", action = "edit")
        }
        gameConnector.update(uuid = game.uuid, request = gameMapper.mapRequest(source = game))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing game.
     *
     * @return view for redirect to page with list of games
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating game.
     *
     * @param uuid UUID of duplicating game
     * @return view for redirect to page with list of games
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        gameConnector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing game.
     *
     * @param uuid UUID of removing game
     * @return view for redirect to page with list of games
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        gameConnector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }


    /**
     * Shows page's view with list of games.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return page's view with list of games
     */
    private fun createListView(model: Model, filter: NameFilterFO, page: Optional<Int>): String {
        val gameFilter = filterMapper.mapNameFilter(source = filter)
        gameFilter.page = page.orElse(1)
        gameFilter.limit = itemsPerPage
        val games = gameConnector.search(filter = gameFilter)

        model.addAttribute("games", games.data)
        model.addAttribute("totalPages", games.pagesCount)
        model.addAttribute("currentPage", games.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Games")
        model.addAttribute("statistics", gameConnector.getStatistics())

        return "game/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param game   FO for game
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, game: GameFO, title: String, action: String): String {
        val formats = registerConnector.getProgramFormats()

        model.addAttribute("game", game)
        model.addAttribute("title", title)
        model.addAttribute("formats", formats)
        model.addAttribute("action", action)

        return "game/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/games"

    }

}
