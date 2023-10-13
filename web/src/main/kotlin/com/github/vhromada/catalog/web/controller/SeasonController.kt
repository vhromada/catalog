package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.RegisterConnector
import com.github.vhromada.catalog.web.connector.SeasonConnector
import com.github.vhromada.catalog.web.connector.ShowConnector
import com.github.vhromada.catalog.web.fo.SeasonFO
import com.github.vhromada.catalog.web.mapper.SeasonMapper
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
 * A class represents controller for seasons.
 *
 * @author Vladimir Hromada
 */
@Controller("seasonController")
@RequestMapping("/shows/{showUuid}/seasons")
class SeasonController(
    /**
     * Connector for seasons
     */
    private val seasonConnector: SeasonConnector,
    /**
     * Connector for shows
     */
    private val showConnector: ShowConnector,
    /**
     * Connector for registers
     */
    private val registerConnector: RegisterConnector,
    /**
     * Mapper for seasons
     */
    private val mapper: SeasonMapper
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of seasons.
     *
     * @param model    model
     * @param showUuid show UUID
     * @param page     index of page
     * @return view for page with list of seasons
     */
    @GetMapping
    fun showList(model: Model, @PathVariable("showUuid") showUuid: String, @RequestParam("page") page: Optional<Int>): String {
        val seasonFilter = PagingFilter()
        seasonFilter.page = page.orElse(1)
        seasonFilter.limit = itemsPerPage
        val seasons = seasonConnector.search(show = showUuid, filter = seasonFilter)

        model.addAttribute("seasons", seasons.data)
        model.addAttribute("totalPages", seasons.pagesCount)
        model.addAttribute("currentPage", seasons.pageNumber)
        model.addAttribute("show", showUuid)
        model.addAttribute("title", "Seasons")

        return "season/index"
    }

    /**
     * Shows page with detail of season.
     *
     * @param model    model
     * @param showUuid show UUID
     * @param uuid     UUID of showing season
     * @return view for page with detail of season
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("showUuid") showUuid: String, @PathVariable("uuid") uuid: String): String {
        val season = seasonConnector.get(show = showUuid, uuid = uuid)

        model.addAttribute("season", season)
        model.addAttribute("show", showUuid)
        model.addAttribute("title", "Season detail")

        return "season/detail"
    }

    /**
     * Shows page for adding season.
     *
     * @param model    model
     * @param showUuid show UUID
     * @return view for page for adding season
     */
    @GetMapping("/add")
    fun showAdd(model: Model, @PathVariable("showUuid") showUuid: String): String {
        showConnector.get(uuid = showUuid)

        val season = SeasonFO(
            uuid = null,
            number = null,
            startYear = null,
            endYear = null,
            language = null,
            subtitles = null,
            note = null
        )
        return createFormView(model = model, season = season, showUuid = showUuid, title = "Add season", action = "add")
    }

    /**
     * Process adding season.
     *
     * @param model    model
     * @param showUuid show UUID
     * @param season   FO for season
     * @param errors   errors
     * @return view for redirect to page with list of seasons (no errors) or view for page for adding season (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @PathVariable("showUuid") showUuid: String, @ModelAttribute("season") @Valid season: SeasonFO, errors: Errors): String {
        require(season.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, season = season, showUuid = showUuid, title = "Add season", action = "add")
        }
        seasonConnector.add(show = showUuid, request = mapper.mapRequest(source = season))

        return getListRedirectUrl(showUuid = showUuid)
    }

    /**
     * Cancel adding season.
     *
     * @param showUuid show UUID
     * @return view for redirect to page with list of seasons
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(@PathVariable("showUuid") showUuid: String): String {
        return getListRedirectUrl(showUuid = showUuid)
    }

    /**
     * Shows page for editing season.
     *
     * @param model    model
     * @param showUuid show UUID
     * @param uuid     UUID of editing season
     * @return view for page for editing season
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("showUuid") showUuid: String, @PathVariable("uuid") uuid: String): String {
        val season = seasonConnector.get(show = showUuid, uuid = uuid)

        return createFormView(model = model, season = mapper.map(source = season), showUuid = showUuid, title = "Edit season", action = "edit")
    }

    /**
     * Process editing season.
     *
     * @param model    model
     * @param showUuid show UUID
     * @param season   FO for season
     * @param errors   errors
     * @return view for redirect to page with list of seasons (no errors) or view for page for editing season (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @PathVariable("showUuid") showUuid: String, @ModelAttribute("season") @Valid season: SeasonFO, errors: Errors): String {
        require(season.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, season = season, showUuid = showUuid, title = "Edit season", action = "edit")
        }
        seasonConnector.update(show = showUuid, uuid = season.uuid, request = mapper.mapRequest(source = season))

        return getListRedirectUrl(showUuid = showUuid)
    }

    /**
     * Cancel editing season.
     *
     * @param showUuid show UUID
     * @return view for redirect to page with list of seasons
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(@PathVariable("showUuid") showUuid: String): String {
        return getListRedirectUrl(showUuid = showUuid)
    }

    /**
     * Process duplicating season.
     *
     * @param showUuid show UUID
     * @param uuid     UUID of duplicating season
     * @return view for redirect to page with list of seasons
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("showUuid") showUuid: String, @PathVariable("uuid") uuid: String): String {
        seasonConnector.duplicate(show = showUuid, uuid = uuid)

        return getListRedirectUrl(showUuid = showUuid)
    }

    /**
     * Process removing season.
     *
     * @param showUuid show UUID
     * @param uuid     UUID of removing season
     * @return view for redirect to page with list of seasons
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("showUuid") showUuid: String, @PathVariable("uuid") uuid: String): String {
        seasonConnector.remove(show = showUuid, uuid = uuid)

        return getListRedirectUrl(showUuid = showUuid)
    }

    /**
     * Returns page's view with form.
     *
     * @param model    model
     * @param season   FO for season
     * @param showUuid show UUID
     * @param title    page's title
     * @param action   action
     * @return page's view with form
     */
    private fun createFormView(model: Model, season: SeasonFO, showUuid: String, title: String, action: String): String {
        val languages = registerConnector.getLanguages()
        val subtitles = registerConnector.getSubtitles()

        model.addAttribute("season", season)
        model.addAttribute("show", showUuid)
        model.addAttribute("languages", languages)
        model.addAttribute("subtitles", subtitles)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "season/form"
    }

    /**
     * Returns redirect URL to list.
     *
     * @param showUuid show UUID
     * @return redirect URL to list
     */
    private fun getListRedirectUrl(showUuid: String): String {
        return "redirect:/shows/$showUuid/seasons"
    }

}
