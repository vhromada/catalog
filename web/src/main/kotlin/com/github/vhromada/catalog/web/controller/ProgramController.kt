package com.github.vhromada.catalog.web.controller

import com.github.vhromada.catalog.web.connector.ProgramConnector
import com.github.vhromada.catalog.web.connector.RegisterConnector
import com.github.vhromada.catalog.web.fo.NameFilterFO
import com.github.vhromada.catalog.web.fo.ProgramFO
import com.github.vhromada.catalog.web.mapper.FilterMapper
import com.github.vhromada.catalog.web.mapper.ProgramMapper
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
 * A class represents controller for programs.
 *
 * @author Vladimir Hromada
 */
@Controller("programController")
@RequestMapping("/programs")
class ProgramController(
    /**
     * Connector for programs
     */
    private val programConnector: ProgramConnector,
    /**
     * Connector for registers
     */
    private val registerConnector: RegisterConnector,
    /**
     * Mapper for programs
     */
    private val programMapper: ProgramMapper,
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
     * Shows page with list of programs.
     *
     * @param model model
     * @param name  name
     * @param page  index of page
     * @return view for page with list of programs
     */
    @GetMapping
    fun showList(model: Model, @RequestParam("name") name: Optional<String>, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = NameFilterFO(name = name.orElse(null)), page = page)
    }

    /**
     * Shows page with filtered list of programs.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return view for page with filtered list of programs
     */
    @PostMapping
    fun filteredList(model: Model, @ModelAttribute("filter") filter: NameFilterFO, @RequestParam("page") page: Optional<Int>): String {
        return createListView(model = model, filter = filter, page = page)
    }

    /**
     * Shows page with detail of program.
     *
     * @param model model
     * @param uuid  UUID of showing program
     * @return view for page with detail of program
     */
    @GetMapping("/{uuid}/detail")
    fun showDetail(model: Model, @PathVariable("uuid") uuid: String): String {
        val program = programConnector.get(uuid = uuid)

        model.addAttribute("program", program)
        model.addAttribute("title", "Program detail")

        return "program/detail"
    }

    /**
     * Shows page for adding program.
     *
     * @param model model
     * @return view for page for adding program
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val program = ProgramFO(
            uuid = null,
            name = null,
            wikiEn = null,
            wikiCz = null,
            mediaCount = null,
            format = null,
            crack = null,
            serialKey = null,
            otherData = null,
            note = null
        )
        return createFormView(model = model, program = program, title = "Add program", action = "add")
    }

    /**
     * Process adding program.
     *
     * @param model   model
     * @param program FO for program
     * @param errors  errors
     * @return view for redirect to page with list of programs (no errors) or view for page for adding program (errors)
     * @throws IllegalArgumentException if UUID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("program") @Valid program: ProgramFO, errors: Errors): String {
        require(program.uuid == null) { "UUID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, program = program, title = "Add program", action = "add")
        }
        programConnector.add(request = programMapper.mapRequest(source = program))

        return LIST_REDIRECT_URL
    }

    /**
     * Process adding program.
     *
     * @return view for redirect to page with list of programs
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing program.
     *
     * @param model model
     * @param uuid  UUID of editing program
     * @return view for page for editing program
     */
    @GetMapping("/edit/{uuid}")
    fun showEdit(model: Model, @PathVariable("uuid") uuid: String): String {
        val program = programConnector.get(uuid = uuid)

        return createFormView(model = model, program = programMapper.map(source = program), title = "Edit program", action = "edit")
    }

    /**
     * Process editing program.
     *
     * @param model   model
     * @param program FO for program
     * @param errors  errors
     * @return view for redirect to page with list of programs (no errors) or view for page for editing program (errors)
     * @throws IllegalArgumentException if UUID is null
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("program") @Valid program: ProgramFO, errors: Errors): String {
        require(program.uuid != null) { "UUID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model = model, program = program, title = "Edit program", action = "edit")
        }
        programConnector.update(uuid = program.uuid, request = programMapper.mapRequest(source = program))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing program.
     *
     * @return view for redirect to page with list of programs
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun cancelEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating program.
     *
     * @param uuid UUID of duplicating program
     * @return view for redirect to page with list of programs
     */
    @GetMapping("/duplicate/{uuid}")
    fun processDuplicate(@PathVariable("uuid") uuid: String): String {
        programConnector.duplicate(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing program.
     *
     * @param uuid UUID of removing program
     * @return view for redirect to page with list of programs
     */
    @GetMapping("/remove/{uuid}")
    fun processRemove(@PathVariable("uuid") uuid: String): String {
        programConnector.remove(uuid = uuid)

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page's view with list of programs.
     *
     * @param model  model
     * @param filter FO for filter for names
     * @param page   index of page
     * @return page's view with list of programs
     */
    private fun createListView(model: Model, filter: NameFilterFO, page: Optional<Int>): String {
        val programFilter = filterMapper.mapNameFilter(source = filter)
        programFilter.page = page.orElse(1)
        programFilter.limit = itemsPerPage
        val programs = programConnector.search(filter = programFilter)

        model.addAttribute("programs", programs.data)
        model.addAttribute("totalPages", programs.pagesCount)
        model.addAttribute("currentPage", programs.pageNumber)
        model.addAttribute("filter", filter)
        model.addAttribute("query", filter.getQuery())
        model.addAttribute("title", "Programs")
        model.addAttribute("statistics", programConnector.getStatistics())

        return "program/index"
    }

    /**
     * Returns page's view with form.
     *
     * @param model   model
     * @param program FO for program
     * @param title   page's title
     * @param action  action
     * @return page's view with form
     */
    private fun createFormView(model: Model, program: ProgramFO, title: String, action: String): String {
        val formats = registerConnector.getProgramFormats()

        model.addAttribute("program", program)
        model.addAttribute("title", title)
        model.addAttribute("formats", formats)
        model.addAttribute("action", action)

        return "program/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/programs"

    }

}
