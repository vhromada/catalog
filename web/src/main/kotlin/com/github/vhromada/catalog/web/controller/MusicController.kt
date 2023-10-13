package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.MusicConnector
import com.github.vhromada.catalog.web.fo.MusicFO
import com.github.vhromada.catalog.web.fo.NameFilterFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import com.github.vhromada.catalog.web.mapper.MusicMapper
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
 * A class represents controller for music.
 *
 * @author Vladimir Hromada
 */
@Controller("musicController")
@RequestMapping("/music")
class MusicController(
    /**
     * Connector for music
     */
    private val musicConnector: MusicConnector,
    /**
     * Mapper for music
     */
    private val musicMapper: MusicMapper,
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
     * Shows page with list of music.
     *
     * @param model model
     * @param name  name
     * @param page  index of page
     * @return view for page with list of music
     */
    @GetMapping
    fun showList(model: Model, @RequestParam("name") name: Optional<String>, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = NameFilterFO(name = name.orElse(null)), page = page)
    }

    /**
     * Shows page with filtered list of music.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return view for page with filtered list of music
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: NameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with detail of music.
     *
     * @param model model
     * @param uuid  UUID of showing music
     * @return view for page with detail of music
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("uuid") uuid: String): String {
        val music = musicConnector.get(uuid = uuid)

        model.addAttribute("music", music)
        model.addAttribute("title", "Music detail")

        return "music/detail"
    }

    /**
     * Shows page for adding music.
     *
     * @param model model
     * @return view for page for adding music
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val music = MusicFO(
            uuid = null,
            name = null,
            wikiEn = null,
            wikiCz = null,
            mediaCount = null,
            note = null
        )
        return createFormView(model = model, music = music, title = "Add music", action = "add")
    }

    /**
     * Process adding music.
     *
     * @param model  model
     * @param music  FO for music
     * @param errors errors
     * @return view for redirect to page with list of music (no errors) or view for page for adding music (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("music") @Valid music: MusicFO, errors: Errors): String {
        require(music.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, music = music, title = "Add music", action = "add")
        }
        musicConnector.add(request = musicMapper.mapRequest(source = music))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding music.
     *
     * @return view for redirect to page with list of music
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing music.
     *
     * @param model model
     * @param uuid  UUID of editing music
     * @return view for page for editing music
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val music = musicConnector.get(uuid = uuid)

        return createFormView(model = model, music = musicMapper.map(source = music), title = "Edit music", action = "edit")
    }

    /**
     * Process editing music.
     *
     * @param model  model
     * @param music  FO for music
     * @param errors errors
     * @return view for redirect to page with list of music (no errors) or view for page for editing music (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("music") @Valid music: MusicFO, errors: Errors): String {
        require(music.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, music = music, title = "Edit music", action = "edit")
        }
        musicConnector.update(uuid = music.uuid, request = musicMapper.mapRequest(source = music))

        return LIST_REDIRECT_URL
    }

    /**
     * Process editing music.
     *
     * @return view for redirect to page with list of music
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating music.
     *
     * @param uuid UUID of duplicating music
     * @return view for redirect to page with list of music
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        musicConnector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing music.
     *
     * @param uuid UUID of removing music
     * @return view for redirect to page with list of music
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        musicConnector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page's view with list of music.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return page's view with list of music
     */
    private fun createListView(model: Model, filter: NameFilterFO, page: Optional<Int>): String {
        val musicFilter = filterMapper.mapNameFilter(source = filter)
        musicFilter.page = page.orElse(1)
        musicFilter.limit = itemsPerPage
        val musics = musicConnector.search(filter = musicFilter)

        model.addAttribute("music", musics.data)
        model.addAttribute("totalPages", musics.pagesCount)
        model.addAttribute("currentPage", musics.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Music")
        model.addAttribute("statistics", musicConnector.getStatistics())

        return "music/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param music  FO for music
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, music: MusicFO, title: String, action: String): String {
        model.addAttribute("music", music)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "music/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/music"

    }

}
