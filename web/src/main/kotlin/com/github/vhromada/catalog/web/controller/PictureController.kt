package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.common.filter.PagingFilter
import com.github.vhromada.catalog.web.connector.PictureConnector
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.util.Optional

/**
 * A class represents controller for pictures.
 *
 * @author Vladimir Hromada
 */
@Controller("pictureController")
@RequestMapping("/pictures")
class PictureController(
    /**
     * Connector for pictures
     */
    private val connector: PictureConnector
) {

    /**
     * Count of items shown on page
     */
    @Value("\${catalog.items_per_page:20}")
    private val itemsPerPage: Int? = null

    /**
     * Shows page with list of pictures.
     *
     * @param model model
     * @param page  index of page
     * @return view for page with list of pictures
     */
    @GetMapping
    fun showList(model: Model, @RequestParam("page") page: Optional<Int>): String {
        val pictureFilter = PagingFilter()
        pictureFilter.page = page.orElse(1)
        pictureFilter.limit = itemsPerPage
        val pictures = connector.search(filter = pictureFilter)

        model.addAttribute("pictures", pictures.data)
        model.addAttribute("totalPages", pictures.pagesCount)
        model.addAttribute("currentPage", pictures.pageNumber)
        model.addAttribute("title", "Pictures")

        return "picture/index"
    }

    /**
     * Get picture content.
     *
     * @param uuid UUID of picture
     * @return picture content
     */
    @GetMapping("/{uuid}")
    fun get(@PathVariable("uuid") uuid: String): ResponseEntity<Resource> {
        val picture = connector.get(uuid = uuid)
            .throwExceptionIfAny()
            .responseEntity!!
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, picture.headers.getFirst(HttpHeaders.CONTENT_DISPOSITION))
            .header(HttpHeaders.CONTENT_TYPE, picture.headers.getFirst(HttpHeaders.CONTENT_TYPE))
            .body(picture.body)
    }

    /**
     * Shows page for adding picture.
     *
     * @param model model
     * @return view for page for adding picture
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        model.addAttribute("title", "Add picture")

        return "picture/form"
    }

    /**
     * Process adding picture.
     *
     * @param file picture
     * @return view for redirect to home page
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(@RequestParam("file") file: MultipartFile): String {
        if (!file.isEmpty) {
            connector.add(picture = file)
        }

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding picture.
     *
     * @return view for redirect to home page
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process removing picture.
     *
     * @param uuid UUID of removing picture
     * @return view for redirect to page with list of pictures
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        connector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/pictures"

    }

}
