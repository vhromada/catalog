package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.EpisodeConnector
import com.github.vhromada.catalog.web.connector.SeasonConnector
import com.github.vhromada.catalog.web.fo.EpisodeFO
import com.github.vhromada.catalog.web.mapper.EpisodeMapper
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
 * A class represents controller for episodes.
 *
 * @author Vladimir Hromada
 */
@Controller("episodeController")
@RequestMapping("/shows/{showUuid}/seasons/{seasonUuid}/episodes")
class EpisodeController(
    /**
     * Connector for episodes
     */
    private val episodeConnector: EpisodeConnector,
    /**
     * Connector for seasons
     */
    private val seasonConnector: SeasonConnector,
    /**
     * Mapper for episodes
     */
    private val mapper: EpisodeMapper
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of episodes.
     *
     * @param model      model
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param page       index of page
     * @return view for page with list of episodes
     */
    @GetMapping
    fun showList(model: Model, @PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String, @RequestParam("page") page: Optional<Int>): String {
        val episodeFilter = PagingFilter()
        episodeFilter.page = page.orElse(1)
        episodeFilter.limit = itemsPerPage
        val episodes = episodeConnector.search(show = showUuid, season = seasonUuid, filter = episodeFilter)

        model.addAttribute("episodes", episodes.data)
        model.addAttribute("totalPages", episodes.pagesCount)
        model.addAttribute("currentPage", episodes.pageNumber)
        model.addAttribute("show", showUuid)
        model.addAttribute("season", seasonUuid)
        model.addAttribute("title", "Episodes")

        return "episode/index"
    }

    /**
     * Shows page with detail of episode.
     *
     * @param model      model
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param uuid       UUID of showing episode
     * @return view for page with detail of episode
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String, @PathVariable("uuid") uuid: String): String {
        val episode = episodeConnector.get(show = showUuid, season = seasonUuid, uuid = uuid)

        model.addAttribute("episode", episode)
        model.addAttribute("show", showUuid)
        model.addAttribute("season", seasonUuid)
        model.addAttribute("title", "Episode detail")

        return "episode/detail"
    }

    /**
     * Shows page for adding episode.
     *
     * @param model      model
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @return view for page for adding episode
     */
    @GetMapping("add")
    fun showAdd(model: Model, @PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String): String {
        seasonConnector.get(show = showUuid, uuid = seasonUuid)

        val episode = EpisodeFO(
            uuid = null,
            number = null,
            length = null,
            name = null,
            note = null
        )
        return createFormView(model = model, episode = episode, showUuid = showUuid, seasonUuid = seasonUuid, title = "Add episode", action = "add")
    }

    /**
     * Process adding episode.
     *
     * @param model      model
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param episode    FO for episode
     * @param errors     errors
     * @return view for redirect to page with list of episodes (no errors) or view for page for adding episode (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(
        model: Model,
        @PathVariable("showUuid") showUuid: String,
        @PathVariable("seasonUuid") seasonUuid: String,
        @ModelAttribute("episode") @Valid episode: EpisodeFO,
        errors: Errors
    ): String {
        require(episode.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, episode = episode, showUuid = showUuid, seasonUuid = seasonUuid, title = "Add episode", action = "add")
        }
        episodeConnector.add(show = showUuid, season = seasonUuid, request = mapper.mapRequest(source = episode))

        return getListRedirectUrl(showUuid = showUuid, seasonUuid = seasonUuid)
    }

    /**
     * Cancel adding episode.
     *
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @return view for redirect to page with list of episodes
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(@PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String): String {
        return getListRedirectUrl(showUuid = showUuid, seasonUuid = seasonUuid)
    }

    /**
     * Shows page for editing episode.
     *
     * @param model      model
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param uuid       UUID of editing episode
     * @return view for page for editing episode
     */
    @GetMapping("edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String, @PathVariable("uuid") uuid: String): String {
        val episode = episodeConnector.get(show = showUuid, season = seasonUuid, uuid = uuid)

        return createFormView(model = model, episode = mapper.map(source = episode), showUuid = showUuid, seasonUuid = seasonUuid, title = "Edit episode", action = "edit")
    }

    /**
     * Process editing episode.
     *
     * @param model      model
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param episode    FO for episode
     * @param errors     errors
     * @return view for redirect to page with list of episodes (no errors) or view for page for editing episode (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(
        model: Model,
        @PathVariable("showUuid") showUuid: String,
        @PathVariable("seasonUuid") seasonUuid: String,
        @ModelAttribute("episode") @Valid episode: EpisodeFO,
        errors: Errors
    ): String {
        require(episode.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, episode = episode, showUuid = showUuid, seasonUuid = seasonUuid, title = "Edit episode", action = "edit")
        }
        episodeConnector.update(show = showUuid, season = seasonUuid, uuid = episode.uuid, request = mapper.mapRequest(source = episode))

        return getListRedirectUrl(showUuid = showUuid, seasonUuid = seasonUuid)
    }

    /**
     * Cancel editing episode.
     *
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @return view for redirect to page with list of episodes
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(@PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String): String {
        return getListRedirectUrl(showUuid = showUuid, seasonUuid = seasonUuid)
    }

    /**
     * Process duplicating episode.
     *
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param uuid       UUID of duplicating episode
     * @return view for redirect to page with list of episodes
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String, @PathVariable("uuid") uuid: String): String {
        episodeConnector.duplicate(show = showUuid, season = seasonUuid, uuid = uuid)

        return getListRedirectUrl(showUuid = showUuid, seasonUuid = seasonUuid)
    }

    /**
     * Process removing episode.
     *
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param uuid       UUID of removing episode
     * @return view for redirect to page with list of episodes
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("showUuid") showUuid: String, @PathVariable("seasonUuid") seasonUuid: String, @PathVariable("uuid") uuid: String): String {
        episodeConnector.remove(show = showUuid, season = seasonUuid, uuid = uuid)

        return getListRedirectUrl(showUuid = showUuid, seasonUuid = seasonUuid)
    }

    /**
     * Returns page's view with form.
     *
     * @param model      model
     * @param episode    FO for episode
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @param title      page's title
     * @param action     action
     * @return page's view with form
     */
    private fun createFormView(model: Model, episode: EpisodeFO, showUuid: String, seasonUuid: String, title: String, action: String): String {
        model.addAttribute("episode", episode)
        model.addAttribute("show", showUuid)
        model.addAttribute("season", seasonUuid)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "episode/form"
    }

    /**
     * Returns redirect URL to list.
     *
     * @param showUuid   show UUID
     * @param seasonUuid season UUID
     * @return redirect URL to list
     */
    private fun getListRedirectUrl(showUuid: String, seasonUuid: String): String {
        return "redirect:/shows/$showUuid/seasons/$seasonUuid/episodes"
    }

}
