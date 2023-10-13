package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.MusicConnector
import com.github.vhromada.catalog.web.connector.SongConnector
import com.github.vhromada.catalog.web.fo.SongFO
import com.github.vhromada.catalog.web.mapper.SongMapper
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
 * A class represents controller for songs.
 *
 * @author Vladimir Hromada
 */
@Controller("songController")
@RequestMapping("/music/{musicUuid}/songs")
class SongController(
    /**
     * Connector for songs
     */
    private val songConnector: SongConnector,
    /**
     * Connector for music
     */
    private val musicConnector: MusicConnector,
    /**
     * Mapper for songs
     */
    private val mapper: SongMapper
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of songs.
     *
     * @param model     model
     * @param musicUuid music UUID
     * @param page      index of page
     * @return view for page with list of songs
     */
    @GetMapping
    fun showList(model: Model, @PathVariable("musicUuid") musicUuid: String, @RequestParam("page") page: Optional<Int>): String {
        val songFilter = PagingFilter()
        songFilter.page = page.orElse(1)
        songFilter.limit = itemsPerPage
        val songs = songConnector.search(music = musicUuid, filter = songFilter)

        model.addAttribute("songs", songs.data)
        model.addAttribute("totalPages", songs.pagesCount)
        model.addAttribute("currentPage", songs.pageNumber)
        model.addAttribute("music", musicUuid)
        model.addAttribute("title", "Songs")

        return "song/index"
    }

    /**
     * Shows page with detail of song.
     *
     * @param model     model
     * @param musicUuid music UUID
     * @param uuid      UUID of showing song
     * @return view for page with detail of song
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("musicUuid") musicUuid: String, @PathVariable("uuid") uuid: String): String {
        val song = songConnector.get(music = musicUuid, uuid = uuid)

        model.addAttribute("song", song)
        model.addAttribute("music", musicUuid)
        model.addAttribute("title", "Song detail")

        return "song/detail"
    }

    /**
     * Shows page for adding song.
     *
     * @param model     model
     * @param musicUuid music UUID
     * @return view for page for adding song
     */
    @GetMapping("/add")
    fun showAdd(model: Model, @PathVariable("musicUuid") musicUuid: String): String {
        musicConnector.get(uuid = musicUuid)

        val song = SongFO(
            uuid = null,
            name = null,
            length = null,
            note = null
        )
        return createFormView(model = model, song = song, musicUuid = musicUuid, title = "Add song", action = "add")
    }

    /**
     * Process adding song.
     *
     * @param model     model
     * @param musicUuid music UUID
     * @param song      FO for song
     * @param errors    errors
     * @return view for redirect to page with list of songs (no errors) or view for page for adding song (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @PathVariable("musicUuid") musicUuid: String, @ModelAttribute("song") @Valid song: SongFO, errors: Errors): String {
        require(song.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, song = song, musicUuid = musicUuid, title = "Add song", action = "add")
        }
        songConnector.add(music = musicUuid, request = mapper.mapRequest(source = song))

        return getListRedirectUrl(musicUuid = musicUuid)
    }

    /**
     * Cancel adding song.
     *
     * @param musicUuid music UUID
     * @return view for redirect to page with list of songs
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(@PathVariable("musicUuid") musicUuid: String): String {
        return getListRedirectUrl(musicUuid = musicUuid)
    }

    /**
     * Shows page for editing song.
     *
     * @param model     model
     * @param musicUuid music UUID
     * @param uuid      UUID of editing song
     * @return view for page for editing song
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("musicUuid") musicUuid: String, @PathVariable("uuid") uuid: String): String {
        val song = songConnector.get(music = musicUuid, uuid = uuid)

        return createFormView(model = model, song = mapper.map(source = song), musicUuid = musicUuid, title = "Edit song", action = "edit")
    }

    /**
     * Process editing song.
     *
     * @param model     model
     * @param musicUuid music UUID
     * @param song      FO for song
     * @param errors    errors
     * @return view for redirect to page with list of songs (no errors) or view for page for editing song (errors)
     * @throws IllegalArgumentException if ID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @PathVariable("musicUuid") musicUuid: String, @ModelAttribute("song") @Valid song: SongFO, errors: Errors): String {
        require(song.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, song = song, musicUuid = musicUuid, title = "Edit song", action = "edit")
        }
        songConnector.update(music = musicUuid, uuid = song.uuid, request = mapper.mapRequest(source = song))

        return getListRedirectUrl(musicUuid = musicUuid)
    }

    /**
     * Cancel editing song.
     *
     * @param musicUuid music UUID
     * @return view for redirect to page with list of songs
     */
    @PostMapping("/edit")
    fun cancelEdit(@PathVariable("musicUuid") musicUuid: String): String {
        return getListRedirectUrl(musicUuid = musicUuid)
    }

    /**
     * Process duplicating song.
     *
     * @param musicUuid music UUID
     * @param uuid      UUID of duplicating song
     * @return view for redirect to page with list of songs
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("musicUuid") musicUuid: String, @PathVariable("uuid") uuid: String): String {
        songConnector.duplicate(music = musicUuid, uuid = uuid)

        return getListRedirectUrl(musicUuid = musicUuid)
    }

    /**
     * Process removing song.
     *
     * @param musicUuid music UUID
     * @param uuid      UUID of removing song
     * @return view for redirect to page with list of songs
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("musicUuid") musicUuid: String, @PathVariable("uuid") uuid: String): String {
        songConnector.remove(music = musicUuid, uuid = uuid)

        return getListRedirectUrl(musicUuid = musicUuid)
    }

    /**
     * Returns page's view with form.
     *
     * @param model     model
     * @param song      FO for song
     * @param musicUuid music UUID
     * @param title     page's title
     * @param action    action
     * @return page's view with form
     */
    private fun createFormView(model: Model, song: SongFO, musicUuid: String, title: String, action: String): String {
        model.addAttribute("song", song)
        model.addAttribute("music", musicUuid)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "song/form"
    }

    /**
     * Returns redirect URL to list.
     *
     * @param musicUuid music UUID
     * @return redirect URL to list
     */
    private fun getListRedirectUrl(musicUuid: String): String {
        return "redirect:/music/$musicUuid/songs"
    }

}
